# Conway's Game of Life - A classic cellular automaton
# Demonstrates 2D arrays and class-based design

import pygame

# Initialize pygame
pygame.init()

# Colors
BLACK = (0, 0, 0)
WHITE = (255, 255, 255)

class Life:
    DIRECTIONS = [(-1,-1), (-1,0), (-1,1), (0,-1), (0,1), (1,-1), (1,0),
            (1,1)]        
    CELL_SIZE = 8
    
    def __init__(self, height, width):
        """Initialize the Life game with given dimensions"""
        self.height = height
        self.width = width
        
        # Create 2D array for the grid (0 = dead, 1 = alive)
        self.grid = self._create_empty_grid()
        
        # Set up pygame window (5x the grid dimensions)
        window_width = width * Life.CELL_SIZE
        window_height = height * Life.CELL_SIZE
        self.window = pygame.display.set_mode((window_width, window_height))
        pygame.display.set_caption("Conway's Game of Life")
        
        # Track changed cells for efficient drawing
        self.changed_cells = []
        
        # Fill window with black background
        self.window.fill(WHITE)
        pygame.display.flip()
    
    def count_living_neighbors(self, row, col):
        """Count living neighbors for a cell, with toroidal wrapping"""
        count = 0

        for dr, dc in Life.DIRECTIONS:
            # Toroidal wrapping
            neighbor_row = (row + dr) % self.height
            neighbor_col = (col + dc) % self.width
            if self.grid[neighbor_row][neighbor_col] == 1:
                count += 1
        return count
    
    def _create_empty_grid(self):
        """Create an empty grid filled with zeros"""
        return [[0 for i in range(self.width)] for j in range(self.height)]
    
    def step(self):
        """Advance one generation using classic 2D loops"""
        # Create new grid for next generation
        new_grid = self._create_empty_grid()
        
        # Clear the changed cells list
        self.changed_cells = []
        
        # Apply Conway's rules to each cell
        for row in range(self.height):
            for col in range(self.width):
                neighbors = self.count_living_neighbors(row, col)
                current_state = self.grid[row][col]
                
                # Apply Conway's rules
                new_grid[row][col] = 1 if (
                    neighbors == 3 or neighbors == 2 and current_state == 1
                ) else 0
                
                # Track changed cells
                if new_grid[row][col] != current_state:
                    self.changed_cells.append((row, col))
        
        # Replace current grid with new generation
        self.grid = new_grid
    
    def toggle_point(self, row, col):
        """Toggle a cell between alive and dead"""
        if 0 <= row < self.height and 0 <= col < self.width:
            self.grid[row][col] = 1 - self.grid[row][col]
            self.changed_cells.append((row, col))
    
    def draw(self):
        """Update only the changed cells on screen"""
        for row, col in self.changed_cells:
            # Calculate screen position
            x = col * Life.CELL_SIZE
            y = row * Life.CELL_SIZE
            
            # Draw the cell
            color = BLACK if self.grid[row][col] == 1 else WHITE
            rect = pygame.Rect(x, y, Life.CELL_SIZE, Life.CELL_SIZE)
            pygame.draw.rect(self.window, color, rect)
        
        # Update only the changed regions (faster than flip)
        if self.changed_cells:
            pygame.display.update()
        
        # Clear the changed cells list
        self.changed_cells = []

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
                elif event.key == pygame.K_c: # Clear the grid
                    life = Life(100, 100)
            elif event.type == pygame.MOUSEBUTTONDOWN:
                mouse_x, mouse_y = pygame.mouse.get_pos()
                col = mouse_x // Life.CELL_SIZE
                row = mouse_y // Life.CELL_SIZE
                life.toggle_point(row, col)
                life.draw()
        
        if not paused:
            life.step()
            life.draw()
        
        clock.tick(10)  # 10 FPS
    
    pygame.quit()

if __name__ == "__main__":
    main()
    
''' Reference patterns for future mods:
PATTERNS = [
    [  # R-pentomino
        [0, 1, 1],
        [1, 1, 0],
        [0, 1, 0]
    ],
    [  # Glider (standard)
        [0, 1, 0],
        [0, 0, 1],
        [1, 1, 1]
    ],
    [  # Lightweight spaceship (LWSS)
        [1, 0, 0, 1, 0],
        [0, 0, 0, 0, 1],
        [1, 0, 0, 0, 1],
        [0, 1, 1, 1, 1]
    ],
    [  # Gosper glider gun
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1],
        [1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
    ]
]
'''
