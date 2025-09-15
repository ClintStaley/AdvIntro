# Conway's Game of Life - A classic cellular automaton
# Demonstrates 2D arrays and class-based design

import pygame

# Initialize pygame
pygame.init()

# Colors
BLACK = (0, 0, 0)
WHITE = (255, 255, 255)

# Game constants
CELL_SIZE = 8

class Life:
    # 5 cell shades changing uniformly from light to dark
    SHADES = [(255, 255, 255), (190, 190, 190), (128, 128, 128), (64, 64, 64),
        (0, 0, 0)]
    # Preconfigured life forms
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
    
    # Key to pattern mapping
    KEY_TO_PATTERN = {
        pygame.K_1: 0,  # R-pentomino
        pygame.K_2: 1,  # Glider
        pygame.K_3: 2,  # Lightweight spaceship
        pygame.K_4: 3   # Gosper glider gun
    }
    
    def __init__(self, height, width):
        """Initialize the Life game with given dimensions"""
        self.height = height
        self.width = width
        
        # Create 2D array for the grid (0 = dead, 1 = alive)
        self.grid = self._create_empty_grid()
        
        # Set up pygame window (5x the grid dimensions)
        window_width = width * CELL_SIZE
        window_height = height * CELL_SIZE
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
        directions = [(-1,-1), (-1,0), (-1,1), (0,-1), (0,1), (1,-1), (1,0),
            (1,1)]
        for dr, dc in directions:
            # Toroidal wrapping
            neighbor_row = (row + dr) % self.height
            neighbor_col = (col + dc) % self.width
            if self.grid[neighbor_row][neighbor_col] != 0:
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
                new_grid[row][col] = min(4, self.grid[row][col] + 1) if (
                    neighbors == 3 or neighbors == 2 and current_state
                ) else 0
                
                # Track changed cells
                if new_grid[row][col] != self.grid[row][col]:
                    self.changed_cells.append((row, col))
        
        # Replace current grid with new generation
        self.grid = new_grid
    
    def toggle_point(self, row, col):
        """Toggle a cell between alive and dead"""
        if 0 <= row < self.height and 0 <= col < self.width:
            self.grid[row][col] = 0 if self.grid[row][col] else 1
            self.changed_cells.append((row, col))
    
    def draw(self):
        """Update only the changed cells on screen"""
        for row, col in self.changed_cells:
            # Calculate screen position
            x = col * CELL_SIZE
            y = row * CELL_SIZE
            
            # Draw the cell
            color = Life.SHADES[self.grid[row][col]]
            rect = pygame.Rect(x, y, CELL_SIZE, CELL_SIZE)
            pygame.draw.rect(self.window, color, rect)
        
        # Update only the changed regions (faster than flip)
        if self.changed_cells:
            pygame.display.update()
        
        # Clear the changed cells list
        self.changed_cells = []
    
    def add_lifeform(self, pattern_index, center_row, center_col):
        """Add a preconfigured life form at the specified location with 
        toroidal wrapping"""
        
        assert pattern_index >= 0 and pattern_index < len(self.PATTERNS), \
            "Invalid pattern index"
            
        pattern = self.PATTERNS[pattern_index]
        pattern_height = len(pattern)
        pattern_width = len(pattern[0])
        
        # Calculate top-left corner of pattern
        start_row = center_row - pattern_height // 2
        start_col = center_col - pattern_width // 2
        
        # Place pattern with toroidal wrapping
        for p_row in range(pattern_height):
            for p_col in range(pattern_width):
                # Calculate grid position with toroidal wrapping
                grid_row = (start_row + p_row) % self.height
                grid_col = (start_col + p_col) % self.width
                    
                # Set cell to alive and mark as changed
                self.grid[grid_row][grid_col] = pattern[p_row][p_col]
                self.changed_cells.append((grid_row, grid_col))

def main():
    """Main game loop"""
    # Create a 100x100 grid
    life = Life(100, 100)
    clock = pygame.time.Clock()
    running = True
    paused = True  # Start paused so user can set up initial pattern
    rate = 10
    
    # Track which keys are currently pressed
    pressed_keys = set()
    
    while running:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                running = False
            elif event.type == pygame.KEYDOWN:
                pressed_keys.add(event.key)
                if event.key == pygame.K_SPACE:
                    paused = not paused
                elif event.key == pygame.K_c: # Clear the grid
                    life = Life(100, 100)
                elif event.key == pygame.K_KP_PLUS: # Double the rate for +
                    rate *= 2
                elif event.key == pygame.K_KP_MINUS: # halve it for -
                    rate /= 2
            elif event.type == pygame.KEYUP:
                pressed_keys.discard(event.key)
            elif event.type == pygame.MOUSEBUTTONDOWN:
                # Get mouse position
                mouse_x, mouse_y = pygame.mouse.get_pos()
                col = mouse_x // CELL_SIZE
                row = mouse_y // CELL_SIZE
                
                # Check if a number key (1-4) is pressed using class lookup table
                pattern_key = None
                for key in pressed_keys:
                    if key in life.KEY_TO_PATTERN:
                        pattern_key = life.KEY_TO_PATTERN[key]
                        break
                
                if pattern_key is not None:
                    # Add life form at mouse position
                    life.add_lifeform(pattern_key, row, col)
                else:
                    # Toggle cell at mouse position
                    life.toggle_point(row, col)
                life.draw()
        
        if not paused:
            life.step()
            life.draw()
        
        clock.tick(rate)  # 10 FPS
    
    pygame.quit()

if __name__ == "__main__":
    main()
