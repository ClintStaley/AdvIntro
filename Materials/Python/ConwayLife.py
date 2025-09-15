# Conway's Game of Life - A classic cellular automaton
# Demonstrates 2D arrays and class-based design
import pygame

# Initialize pygame
pygame.init()

# Colors
BLACK = (0, 0, 0)
WHITE = (255, 255, 255)

class Life:
    def __init__(self, height, width):
        """Initialize the Life game with given dimensions"""
        self.height = height
        self.width = width
        self.cell_size = 5
        
        # Create 2D array for the grid (0 = dead, 1 = alive)
        self.grid = []
        for row in range(height):
            new_row = []
            for col in range(width):
                new_row.append(0)
            self.grid.append(new_row)
        
        # Set up pygame window (5x the grid dimensions)
        window_width = width * self.cell_size
        window_height = height * self.cell_size
        self.window = pygame.display.set_mode((window_width, window_height))
        pygame.display.set_caption("Conway's Game of Life")
    
        # Fill window with white background
        self.window.fill(WHITE)
    
    def count_neighbors(self, row, col):
        """Count living neighbors for a cell, with toroidal wrapping"""
        count = 0
        for dr in [-1, 0, 1]:
            for dc in [-1, 0, 1]:
                # Toroidal wrapping
                neighbor_row = (row + dr) % self.height
                neighbor_col = (col + dc) % self.width
                if self.grid[neighbor_row][neighbor_col] == 1:
                    count += 1
        return count
    
    def step(self):
        """Advance one generation using classic 2D loops"""
        # Create new grid for next generation
        new_grid = []
        for row in range(self.height):
            new_row = []
            for col in range(self.width):
                new_row.append(0)
            new_grid.append(new_row)
        
        # Apply Conway's rules to each cell
        for row in range(self.height):
            for col in range(self.width):
                neighbors = self.count_neighbors(row, col)
                current_state = self.grid[row][col]
                
                # Apply Conway's rules
                if current_state == 1:  # Currently alive
                    if neighbors == 2 or neighbors == 3:
                        new_grid[row][col] = 1  # Survives
                    else:
                        new_grid[row][col] = 0  # Dies
                else:  # Currently dead
                    if neighbors == 3:
                        new_grid[row][col] = 1  # Becomes alive
                    else:
                        new_grid[row][col] = 0  # Stays dead
                
                # Track changed cells
                if new_grid[row][col] != current_state:
                    self.changed_cells.append((row, col))
        
        # Replace current grid with new generation
        self.grid = new_grid
    
    def toggle_point(self, row, col):
        """Toggle a cell between alive and dead"""
        if 0 <= row < self.height and 0 <= col < self.width:
            self.grid[row][col] = 1 - self.grid[row][col]
    
    def draw(self):
        """Update only the changed cells on screen"""
        for row, col in self.changed_cells:
            # Calculate screen position
            x = col * self.cell_size
            y = row * self.cell_size
            
            # Draw the cell
            color = BLACK if self.grid[row][col] == 1 else WHITE
            rect = pygame.Rect(x, y, self.cell_size, self.cell_size)
            pygame.draw.rect(self.window, color, rect)
        
        # Update only the changed regions (faster than flip)
        if self.changed_cells:
            pygame.display.update()
        
def main():
    """Main game loop"""
    # Create a 100x100 grid
    life = Life(100, 100)
    clock = pygame.time.Clock()
    running = True
    paused = True  # Start paused so user can set up initial pattern
    
    while running:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                running = False
            elif event.type == pygame.KEYDOWN:
                if event.key == pygame.K_SPACE:
                    paused = not paused
                elif event.key == pygame.K_c:
                    # Clear the grid
                    life = Life(100, 100)
            elif event.type == pygame.MOUSEBUTTONDOWN:
                # Toggle cell at mouse position
                mouse_x, mouse_y = pygame.mouse.get_pos()
                col = mouse_x // life.cell_size
                row = mouse_y // life.cell_size
                life.toggle_point(row, col)
                life.draw()
        
        if not paused:
            life.step()
            life.draw()
        
        clock.tick(10)  # 10 FPS
    
    pygame.quit()

if __name__ == "__main__":
    main()
