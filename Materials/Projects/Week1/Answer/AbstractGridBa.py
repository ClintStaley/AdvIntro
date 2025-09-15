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

ring_colors = (GREEN, BLUE, RED, WHITE)

def draw_pattern():
    for x in range(0, WIDTH):
        for y in range(0, WIDTH):
            in_region = False
            for rx, ry, rw, rh in regions:
                if rx <= x < rx + rw and ry <= y < ry + rh:
                    in_region = True
                    break
            if in_region:
                # if clear square, check distance and draw bullseye
                if (x // CELL_SIZE % 2 == y // CELL_SIZE % 2):
                    # distance from center in RING_WIDTHs, at most 3
                    dist = min(int(math.sqrt((x-CENTER)**2 + (y-CENTER)**2)
                        / RING_WIDTH), 3)
                    color = ring_colors[dist]
                else:
                    color = BLACK
            else:
                color = GRAY
                
            window.set_at((x, y), color)

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
