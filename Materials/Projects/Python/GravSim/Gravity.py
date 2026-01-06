import numpy as np
import pygame
import sys

class GravitySimulator:
    def __init__(self, positions, masses, velocities, G=1.0):
        self.positions = positions
        self.masses = masses
        self.G = G
        self.velocities = velocities
        self.sim_time = 0

        assert len(positions) == len(masses) == len(velocities), \
            "Positions, masses, and velocities must have the same length"
            
        # Compute drawing radii as the cube root of the masses, but at least 1.
        self.radii = np.minimum(np.cbrt(masses), 1).astype(int)

    def update_physics(self, dt=0.1):
        # print(f"Closest pair: {self._find_closest_pair()}")
        forces = self.compute_gravitational_forces()
        
        # Q1: Wh do we need the np.newaxis?
        accelerations = forces / self.masses[:, np.newaxis]
        self.velocities += accelerations * dt
        self.positions += self.velocities * dt
        self.sim_time += dt

    # Helper method to find the closest pair of particles. Very slow.
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
    
    def compute_gravitational_forces(self):
        """Compute gravitational forces between all pairs of circles"""
        n = len(self.positions)
        
        # Create position matrices for pairwise calculations
        pos_i = self.positions[:, np.newaxis, :]  # Shape: (n, 1, 2)
        pos_j = self.positions[np.newaxis, :, :]  # Shape: (1, n, 2)
        
        # Vectorized distance calculations
        # distances[i,j,:] is pos_j[j] - pos_i[i], the 2-element vector from 
        # particle i to particle j.  Each row of distances is the vectors from
        # the particle of that row to all the others.
        distances = pos_j - pos_i  # Shape: (n, n, 2)
        
        # Q1: With n = 5, how many squares are computed here?
        distances_squared = np.sum(distances**2, axis=2)  # Shape: (n, n)
        
        # Avoid division by zero (self-interaction, and very close particles)
        distances_squared[distances_squared == 0] = 1.0
        
        # Force magnitudes: F = G * m1 * m2 / r^2.  Outer product of masses.
        force_magnitudes = self.G * self.masses[:, np.newaxis] * \
            self.masses[np.newaxis, :] / distances_squared  # Shape: (n, n)
        
        # Force directions (unit vectors). Shape: (n, n, 2)
        # Q2: Is this a symmetric matrix?  Was force_magnitudes symmetric?
        distances_magnitude = np.sqrt(distances_squared)
        unit_distances = distances / distances_magnitude[:, :, np.newaxis]
        
        # Total forces on each particle. Shape: (n, n, 2)
        forces = force_magnitudes[:, :, np.newaxis] * unit_distances
        
        # Total force on each particle p, pointing from p to all other particles.
        # Q3: Could this be done with axis=0?
        total_forces = np.sum(forces, axis=1)  
        
        # Q4: What shape is total_forces?
        return total_forces
    
    # Rotate the view matrix ccw or cw by .1 radians
    def rotate_ccw(self):
        pass        
    def rotate_cw(self):
        pass     

    # Scale the view matrix up or down by 10%
    def scale_up(self):
        pass        
    def scale_down(self):   
        pass        
    
    # Draw the system in the window, with masses as circle, centered on the
    # position and with a radius equal to the cube root of the mass.
    def draw_system(self, window):
        window.fill((0, 0, 0))
        pygame.display.set_caption(f"Time: {self.sim_time:.2f}")
        
        
        for i in range(len(self.positions)):
            pygame.draw.circle(window, (255, 255, 255),
                self.positions[i].astype(int), self.radii[i])
        pygame.display.flip()

    # Static constructor for a random cloud of n random masses between 1 and 10,
    # positions uniformly distributed within a circle of given radius centered 
    # at origin and random velocities of at most magnitude .001 of the radius.
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
        pass

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
        
        print(positions.shape)
        print(f"positions: {positions}")
        
        # First mass is mCenter; rest are 1
        masses = np.append(np.array([mCenter]), np.ones(n))
        
        print(masses.shape)
        print(f"masses: {masses}")
        
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
        
        print(velocities.shape)
        print(f"velocities: {velocities}")
        
        # Convert positions to float32
        positions = positions.astype(np.float32)
        
        return GravitySimulator(positions, masses, velocities, G=g)
    
def main():
    pygame.init()
    window = pygame.display.set_mode((1200, 800))
    pygame.display.set_caption("Gravity")
    clock = pygame.time.Clock()
    
    simulator = GravitySimulator.create_orbital_system(100, 5, 20.0)
    # simulator = GravitySimulator.create_random_system(100, 20, 20.0)

    while True:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()
            if event.type == pygame.KEYDOWN:
                # Double or half the speed when keypad +/- are pressed
                if event.key == pygame.K_KP_PLUS:
                    pass
                if event.key == pygame.K_KP_MINUS:
                    pass
                # Rotate ccw or cw when left or right arrow keys are pressed
                if event.key == pygame.K_LEFT:
                    pass
                if event.key == pygame.K_RIGHT:
                    pass
                # Scale up or down when up or down arrow keys are pressed
                if event.key == pygame.K_UP:
                    pass
                if event.key == pygame.K_DOWN:
                    pass
        
        simulator.update_physics(.01)
        simulator.draw_system(window)
        clock.tick(1)

if __name__ == "__main__":
    main()