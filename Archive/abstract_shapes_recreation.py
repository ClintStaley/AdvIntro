import pygame
import math

pygame.init()
WIDTH, HEIGHT = 800, 600
screen = pygame.display.set_mode((WIDTH, HEIGHT))
pygame.display.set_caption("Abstract Shapes Recreation")

# Colors
GRAY = (128, 128, 128)
BLACK = (0, 0, 0)
WHITE = (255, 255, 255)
RED = (255, 0, 0)
BLUE = (0, 0, 255)
GREEN = (0, 255, 0)

# Grid parameters
CELL_SIZE = 20
CENTER_X, CENTER_Y = WIDTH // 2, HEIGHT // 2

# Define the four rectangular regions (x, y, width, height)
regions = [
    (50, 50, 320, 320),    # Large top-left square
    (450, 80, 60, 280),    # Vertical rectangle (right)
    (200, 400, 120, 160),  # Bottom-left rectangle
    (350, 430, 120, 120)   # Bottom-right rectangle
]

def get_distance_color(x, y, center_x, center_y, max_dist):
    """Return color based on distance from center"""
    dist = math.sqrt((x - center_x)**2 + (y - center_y)**2)
    if dist < max_dist * 0.3:
        return GREEN
    elif dist < max_dist * 0.6:
        return BLUE
    elif dist < max_dist:
        return RED
    return None

def draw_pattern():
    screen.fill(GRAY)
    
    # Process each pixel
    for y in range(HEIGHT):
        for x in range(WIDTH):
            # Check if pixel is in any region
            in_region = False
            for rx, ry, rw, rh in regions:
                if rx <= x < rx + rw and ry <= y < ry + rh:
                    in_region = True
                    
                    # Checkerboard pattern
                    grid_x = (x - rx) // CELL_SIZE
                    grid_y = (y - ry) // CELL_SIZE
                    
                    if (grid_x + grid_y) % 2 == 0:
                        # Black squares
                        screen.set_at((x, y), BLACK)
                    else:
                        # White squares, but check for colored overlay
                        color = get_distance_color(x, y, CENTER_X, CENTER_Y, 200)
                        screen.set_at((x, y), color if color else WHITE)
                    break
            
            # If not in any region, keep gray background

# Draw the pattern
draw_pattern()

# Main loop
running = True
clock = pygame.time.Clock()

while running:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False
        elif event.type == pygame.KEYDOWN:
            if event.key == pygame.K_SPACE:
                draw_pattern()
    
    pygame.display.flip()
    clock.tick(60)

pygame.quit()