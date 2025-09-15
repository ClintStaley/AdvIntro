import numpy as np

def compute_gravitational_forces(positions, masses, G=1.0):
    """Compute gravitational forces between all pairs of circles"""
    n = len(positions)
    
    # Create position matrices for pairwise calculations
    pos_i = positions[:, np.newaxis, :]  # Shape: (n, 1, 2)
    pos_j = positions[np.newaxis, :, :]  # Shape: (1, n, 2)
    
    # Vectorized distance calculations
    distances = pos_j - pos_i  # Shape: (n, n, 2)
    distances_squared = np.sum(distances**2, axis=2)  # Shape: (n, n)
    
    # Avoid division by zero (self-interaction)
    distances_squared[distances_squared == 0] = 1.0
    
    # Force magnitudes: F = G * m1 * m2 / r^2
    force_magnitudes = G * masses[:, np.newaxis] * masses[np.newaxis, :] / distances_squared
    
    # Force directions (unit vectors)
    distances_magnitude = np.sqrt(distances_squared)
    force_directions = distances / distances_magnitude[:, :, np.newaxis]
    
    # Total forces on each particle
    forces = force_magnitudes[:, :, np.newaxis] * force_directions
    total_forces = np.sum(forces, axis=1)  # Sum over all other particles
    
    return total_forces

def update_physics(positions, velocities, masses, dt=0.1):
    """Update positions and velocities using gravitational forces"""
    forces = compute_gravitational_forces(positions, masses)
    
    # F = ma, so a = F/m
    accelerations = forces / masses[:, np.newaxis]
    
    # Update velocities and positions
    velocities += accelerations * dt
    positions += velocities * dt
    
    return positions, velocities