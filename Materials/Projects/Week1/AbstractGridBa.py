import pygame
import math

WIDTH = 800
CENTER = WIDTH // 2

# Grid parameters
CELL_SIZE = 40
RING_WIDTH = 100

# Define the four rectangular regions (x, y, width, height)
regions = (
    (50, 100, 350, 350),    # Large top-left square
    (550, 100, 50, 300),    # Vertical rectangle (right)
    (450, 450, 100, 250),   # Bottom-left rectangle
    (600, 500, 100, 200)    # Bottom-right rectangle
)

# Colors
WHITE = (255, 255, 255)
RED = (255, 0, 0)
BLUE = (0, 0, 255)
GREEN = (0, 255, 0)
GRAY = (128, 128, 128)
BLACK = (0, 0, 0)

pygame.init()
window = pygame.display.set_mode((WIDTH, WIDTH))
pygame.display.set_caption("Abstract Grid")

# Fill IN HERE

draw_pattern()

# Main loop
running = True
clock = pygame.time.Clock()

while running:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            # save the screen to a file
            pygame.image.save(window, "AbstractGridBa.png")
            running = False
    
    pygame.display.flip()
    clock.tick(10)

pygame.quit()