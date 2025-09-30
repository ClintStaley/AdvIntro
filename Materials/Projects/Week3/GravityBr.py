import numpy as np
import pygame
import sys

class GravitySimulator:
    # 2-D Numpy array to rotate ccw by .1 radians.
    CCW_ROT = np.array([[np.cos(.1), -np.sin(.1)],
                                [np.sin(.1), np.cos(.1)]])
    CW_ROT = np.array([[np.cos(-.1), -np.sin(-.1)],
                                [np.sin(-.1), np.cos(-.1)]])
    # Matrices to scale by a factor of 1.1 and 0.9, respectively.
    SCALE_UP = np.array([[1.1, 0],
                            [0, 1.1]])
    SCALE_DOWN = np.array([[0.9, 0],
                            [0, 0.9]])
    
    def __init__(self, positions, masses, velocities, G=1.0):
        self.positions = positions
        self.masses = masses
        self.G = G
        self.velocities = velocities
        self.sim_time = 0
        # Initialize view transformation matrix to identity
        self.viewXfm = np.eye(2)
        assert len(positions) == len(masses) == len(velocities), \
            "Positions, masses, and velocities must have the same length"
            
        # Compute drawing radii as the cube root of the masses, but at least 1.
        self.radii = np.maximum(np.cbrt(self.masses), 1)

    def update_physics(self, dt=0.1):
        # print(f"Closest pair: {self._find_closest_pair_vectorized()}")
        forces = self.compute_gravitational_forces()
        accelerations = forces / self.masses[:, np.newaxis]
        self.velocities += accelerations * dt
        self.positions += self.velocities * dt
        self.sim_time += dt

    # Helper method to find the closest pair of particles.  Makes no use of
    # numpy or vectorized operations.
    def _find_closest_pair(self):
        """Find the closest particles to each particle"""
        n = len(self.positions)
        closest_pair = np.zeros(2, dtype=int)
        closest_distance = float('inf')
        for i in range(n):
            for j in range(i+1, n):
                distance = np.linalg.norm(self.positions[i] - self.positions[j])
                if distance < closest_distance:
                    closest_distance = distance
                    closest_pair = [i, j]
        return (closest_pair, closest_distance)
    
    # Find the closest pair of particles using numpy and vectorized operations.
    def _find_closest_pair_vectorized(self):
        # Find all pairs squared distances
        n = len(self.positions)
        distances_squared = np.sum((self.positions[:, np.newaxis, :]
            - self.positions[np.newaxis, :, :])**2, axis=2)
        # Set diagonal to inf to avoid self-interaction
        
        np.fill_diagonal(distances_squared, float('inf'))
        
        # Find the flat index of the closest pair. "Unravel" argmin by hand
        pair_index = np.argmin(distances_squared)
        rtn_ndx = [pair_index // n, pair_index % n]
        return (rtn_ndx, np.sqrt(distances_squared[rtn_ndx[0], rtn_ndx[1]]))
    
    def compute_gravitational_forces(self):
        """Compute gravitational forces between all pairs of circles"""
        n = len(self.positions)
        
        # Create position matrices for pairwise calculations
        pos_i = self.positions[:, np.newaxis, :]  # Shape: (n, 1, 2)
        pos_j = self.positions[np.newaxis, :, :]  # Shape: (1, n, 2)
        
        # Vectorized distance calculations
        distances = pos_j - pos_i  # Shape: (n, n, 2)
        distances_squared = np.sum(distances**2, axis=2)  # Shape: (n, n)
        
        # Avoid division by zero (self-interaction)
        distances_squared[distances_squared < 4] = 4.0
        
        # Force magnitudes: F = G * m1 * m2 / r^2
        force_magnitudes = self.G * self.masses[:, np.newaxis] * \
            self.masses[np.newaxis, :] / distances_squared  # Shape: (n, n)
        
        # Force directions (unit vectors). Shape: (n, n, 2)
        distances_magnitude = np.sqrt(distances_squared)
        force_directions = distances / distances_magnitude[:, :, np.newaxis]
        
        # Total forces on each particle. Shape: (n, n, 2)
        forces = force_magnitudes[:, :, np.newaxis] * force_directions
        total_forces = np.sum(forces, axis=1)  # Sum over all other particles
        
        return total_forces
    
    # Rotate the view ccw or cw by .1 radians
    def rotate_ccw(self):
        self.viewXfm = self.CCW_ROT @ self.viewXfm
    
    def rotate_cw(self):
        self.viewXfm = self.CW_ROT @ self.viewXfm
    
    def scale_up(self):
        self.viewXfm = self.SCALE_UP @ self.viewXfm
    
    def scale_down(self):   
        self.viewXfm = self.SCALE_DOWN @ self.viewXfm
    
    # Draw the system in the window, with masses as circle, centered on the
    # position and with a radius equal to the cube root of the mass.
    def draw_system(self, window):
        window.fill((0, 0, 0))
        pygame.display.set_caption(f"Time: {self.sim_time:.2f}")
        
        # Get window center
        window_center = np.array([window.get_width() // 2, 
                                 window.get_height() // 2])
        
        # Apply view transformation to positions and center in window
        transformed_positions = self.positions @ self.viewXfm.T + window_center
        
        for i in range(len(transformed_positions)):
            pygame.draw.circle(window, (255, 255, 255),
                transformed_positions[i].astype(int), self.radii[i])
        pygame.display.flip()

    # Static constructor for a random cloud of n random masses between 1 and 10,
    # positions uniformly distributed within a circle of given radius centered 
    # at origin and random velocities of at most magnitude .001 of the radius, 
    # using the given g.  
    # 
    # 1. To place a random particle, choose a random angle between 0 and tau,
    # and a random distance based on the sqrt of a random 0-1 value, as
    # discussed in class.  
    # 2. Choose velocities with a single rand call and subsequent math on its
    # result.  Then, ensure total momentum is zero, by computing the actual
    # momentum, the system velocity, and then subtracting that velocity from 
    # all velocities.
    #
    # USE NO LOOPS.  Do all this with numpy vectorized operations.
    @staticmethod
    def create_random_system(n=10, radius=100, g=1.0):
        # Generate uniform distribution within circle
        angles = np.random.rand(n) * 2 * np.pi
        distances = np.sqrt(np.random.rand(n)) * radius
        positions = np.stack([distances * np.cos(angles), 
                                   distances * np.sin(angles)], axis=1)
        masses = np.random.rand(n) * 10 + 1
        velocities = np.random.rand(n, 2) * 0.1 * radius
        total_momentum = np.sum(masses[:, np.newaxis] * velocities, axis=0)
        velocities -= total_momentum / np.sum(masses)
        return GravitySimulator(positions, masses, velocities, G=g)

    # Static constructor an orbital "system" with one central mass mCenter, 
    # and a number n of satellites, at distances 20, 30, 40, etc. 
    # from the central mass, with masses 1 and with velocities that will hold 
    # them in orbit, assuming the given g. Vary the velocities by a random 
    # factor of 0 to .1 so as to produce elliptical orbits.
    @staticmethod
    def create_orbital_system(mCenter, n=10, g=1.0):
        # positions are at distances 1, 2, 3, etc. from the central mass
        positions = np.array([[0, 0]]) # Central mass
        positions = np.append(positions,
            np.array([[10 + 10*i, 0] for i in range(1, n+1)]), axis=0)
        
        # First mass is mCenter; rest are 1
        masses = np.append(np.array([mCenter]), np.ones(n))
        
        # compute magnitudes of velocities of satellites using the formula
        # v = sqrt(G * mCenter / distance)
        magnitudes = np.sqrt(g * masses[0] / positions[1:, 0]).reshape(n, 1)
        # vary the magnitudes by a random factor of 0 to .1 so as to produce 
        # elliptical orbits
        magnitudes = magnitudes * (1 + np.random.rand(n, 1) * 0.1)
        
        # Add a column of 0s to the magnitudes array to produce perpendicular 
        # velocities
        magnitudes = np.append(np.zeros((n, 1)), magnitudes, axis=1)
        
        # Insert initial velocity of 0,0 in the first row to arrive at velocities
        velocities = np.insert(magnitudes, 0, [0, 0], axis=0)
        
        # Convert positions to float32
        positions = positions.astype(np.float32)
        
        return GravitySimulator(positions, masses, velocities, G=g)
    
def main():
    pygame.init()
    window = pygame.display.set_mode((1200, 800))
    pygame.display.set_caption("Gravity")
    clock = pygame.time.Clock()
    steps_per_frame = 1 # number of .1 second simultation steps per frame
    
    # simulator = GravitySimulator.create_orbital_system(100, 20, 200.0)
    simulator = GravitySimulator.create_random_system(100, 200, 100.0)

    while True:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()
            # Double or half the speed when keypad +/- are pressed
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_KP_PLUS:
                    steps_per_frame *= 2
                if event.key == pygame.K_KP_MINUS:
                    steps_per_frame = max(1, steps_per_frame // 2)
                # Rotate ccw or cw when left or right arrow keys are pressed
                if event.key == pygame.K_LEFT:
                    simulator.rotate_ccw()
                if event.key == pygame.K_RIGHT:
                    simulator.rotate_cw()
                # Scale up or down when up or down arrow keys are pressed
                if event.key == pygame.K_UP:
                    simulator.scale_up()
                if event.key == pygame.K_DOWN:
                    simulator.scale_down()
        
        for _ in range(steps_per_frame):
            simulator.update_physics(.01)
        simulator.draw_system(window)
        clock.tick(100)

if __name__ == "__main__":
    main()