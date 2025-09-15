import pygame
import math
import random

# Initialize Pygame
pygame.init()

# Set up the display
WIDTH, HEIGHT = 800, 600
screen = pygame.display.set_mode((WIDTH, HEIGHT))
pygame.display.set_caption("Artistic Shapes with Pygame")

# Colors (RGB tuples)
BLACK = (0, 0, 0)
WHITE = (255, 255, 255)
RED = (255, 0, 0)
BLUE = (0, 100, 255)
GREEN = (0, 255, 100)
PURPLE = (150, 0, 255)

def draw_spiral_circles(surface, center_x, center_y):
    """Draw a spiral of colorful circles - demonstrates loops and math"""
    for i in range(50):
        # Calculate position using trigonometry
        angle = i * 0.3
        radius = i * 4
        x = center_x + int(radius * math.cos(angle))
        y = center_y + int(radius * math.sin(angle))
        
        # Vary circle size and color based on position
        circle_radius = 10 + i // 5
        color_intensity = (i * 5) % 255
        color = (color_intensity, 100, 255 - color_intensity)
        
        pygame.draw.circle(surface, color, (x, y), circle_radius)

def draw_grid_pattern(surface):
    """Draw a grid of rectangles with varying colors - demonstrates nested loops"""
    rect_size = 40
    for row in range(0, HEIGHT, rect_size):
        for col in range(0, WIDTH, rect_size):
            # Create color based on position
            red_value = (row + col) % 255
            green_value = (row * 2) % 255
            blue_value = (col * 2) % 255
            color = (red_value, green_value, blue_value)
            
            # Draw rectangle
            pygame.draw.rect(surface, color, (col, row, rect_size-2, rect_size-2))

def draw_concentric_shapes(surface, center_x, center_y):
    """Draw concentric circles and squares - demonstrates loops with conditionals"""
    for i in range(20, 200, 15):
        if i % 30 == 0:  # Every other iteration, draw a square
            # Calculate square coordinates
            half_size = i // 2
            rect = (center_x - half_size, center_y - half_size, i, i)
            color = (255 - i, i, 150)
            pygame.draw.rect(surface, color, rect, 3)  # 3 = line width (unfilled)
        else:  # Draw a circle
            color = (100, 255 - i, i)
            pygame.draw.circle(surface, color, (center_x, center_y), i, 2)

def draw_pixel_art(surface, start_x, start_y):
    """Draw individual pixels in a pattern - demonstrates pixel-level control"""
    pixel_size = 3
    for y in range(50):
        for x in range(50):
            # Create a pattern using modular arithmetic
            if (x + y) % 7 == 0 or (x * y) % 13 == 0:
                # Calculate color based on position
                red = (x * 5) % 255
                green = (y * 5) % 255
                blue = (x + y * 2) % 255
                color = (red, green, blue)
                
                # Draw a small rectangle to represent a "pixel"
                pixel_rect = (start_x + x * pixel_size, start_y + y * pixel_size, 
                             pixel_size, pixel_size)
                pygame.draw.rect(surface, color, pixel_rect)

# Main program loop
def main():
    clock = pygame.time.Clock()
    running = True
    
    while running:
        # Handle events
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                running = False
            elif event.type == pygame.KEYDOWN:
                if event.key == pygame.K_SPACE:
                    # Redraw when spacebar is pressed
                    screen.fill(BLACK)
                    
                    # Draw different artistic patterns
                    draw_spiral_circles(screen, WIDTH//4, HEIGHT//4)
                    draw_concentric_shapes(screen, 3*WIDTH//4, HEIGHT//4)
                    draw_grid_pattern(screen)
                    draw_pixel_art(screen, WIDTH//2 - 75, HEIGHT//2 + 50)
                    
                    pygame.display.flip()
        
        clock.tick(10)  # 10 FPS
    
    pygame.quit()

# Initial drawing
screen.fill(BLACK)
draw_spiral_circles(screen, WIDTH//4, HEIGHT//4)
draw_concentric_shapes(screen, 3*WIDTH//4, HEIGHT//4)
draw_grid_pattern(screen)
draw_pixel_art(screen, WIDTH//2 - 75, HEIGHT//2 + 50)

pygame.display.flip()

# Run the main loop
if __name__ == "__main__":
    main()