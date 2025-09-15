import pygame

WIDTH = 800

# Colors
WHITE = (255, 255, 255)
RED = (255, 0, 0)
BLUE = (0, 0, 255)
GREEN = (0, 255, 0)
CYAN = (0, 255, 255)
MAGENTA = (255, 0, 255)
YELLOW = (255, 255, 0)
GRAY = (128, 128, 128)
BLACK = (0, 0, 0)

pygame.init()
window = pygame.display.set_mode((WIDTH, WIDTH))
pygame.display.set_caption("Abstract Grid")

# Draw bullseye pattern, centered in the rectangle given, and extending to
# its edges.  Shade gradually from black at the center to the given color at the
# edge.  Fill in area outside of the bullseye as white.  Use just one circle call.
def draw_bullseye(window, x, y, width, height, color):
    # fill the rectangle with white
    pygame.draw.rect(window, WHITE, (x, y, width, height))
    
    center = (x + width // 2, y + height // 2) 
    radius = min(width, height) // 2
    
    # Draw bullseye by drawing a circle with the given color and radius, and
    # successively smaller circles, gradually fading from color to BLACK.
    for i in range(radius, 0, -1):
        new_color = (color[0] * i // radius, 
                 color[1] * i // radius, 
                 color[2] * i // radius)
        pygame.draw.circle(window, new_color, center, i)
    
# Fill the given rectangle with a grid of circles of the given radius and color
# or with bullseyes of the given radius and color if indicated
def draw_circles_grid(window, x, y, width, height, radius, color, bullseye=False):
    pygame.draw.rect(window, WHITE, (x, y, width, height))
    for i in range(x+radius, x + width + radius, radius*2):
        for j in range(y+radius, y + height + radius, radius*2):
            if bullseye:
                draw_bullseye(window, i-radius, j-radius, radius*2, radius*2, color)
            else:
                pygame.draw.circle(window, color, (i, j), radius)
        
# draw a rectangular spiral with the given color in the given rectangle. 
# Start from the upper left and spiral inward, clockwise.  Do the following
# in a loop, as long as the rectangle is at least 4 pixels wide and tall.  
# 1. Draw a 2-pixel wide line across the top, and shave 8 pixels off the top.
# 2. Draw a 2-pixel wide line down the right, and shave 8 pixels off the right.
# 3. Draw a 2-pixel wide line across the bottom, and shave 8 pixels off the bottom.
# 4. Draw a 2-pixel wide line left the left, and shave 8 pixels off the left.
def draw_spiral(window, x, y, width, height, color):
    pygame.draw.rect(window, WHITE, (x, y, width, height))
    while width >= 4 and height >= 4:
        pygame.draw.line(window, color, (x, y), (x + width-1, y), 4)
        pygame.draw.line(window, color, (x+width-1, y),
            (x+width-1, y + height-1), 4)
        pygame.draw.line(window, color, (x + width-1, y+height-1),
            (x, y+height-1),  4)
        pygame.draw.line(window, color, (x, y+height-1), (x, y+8), 4)
        pygame.draw.line(window, color, (x, y+8), (x+8, y+8), 4)
        x += 8
        y += 8
        height -= 16
        width -= 16

draw_bullseye(window, 0, 0, WIDTH // 3, WIDTH // 3, RED)
draw_circles_grid(window, WIDTH // 3, 0, WIDTH // 3, WIDTH // 3, 50, GRAY, True)
draw_spiral(window, 2*WIDTH // 3, 0, WIDTH // 3, WIDTH // 3, GREEN)

draw_circles_grid(window, 0, WIDTH // 3, WIDTH // 3, WIDTH // 3, 30,
    YELLOW, True)
draw_bullseye(window, WIDTH // 3, WIDTH // 3, WIDTH // 3, WIDTH // 3, GRAY)
draw_circles_grid(window, 2*WIDTH // 3, WIDTH // 3, WIDTH // 3, WIDTH // 3, 15, BLUE)
draw_spiral(window, 0, 2*WIDTH // 3, WIDTH // 3, WIDTH // 3, CYAN)
draw_bullseye(window, WIDTH // 3, 2*WIDTH // 3, WIDTH // 3, WIDTH // 3, MAGENTA)
draw_circles_grid(window, 2*WIDTH // 3, 2*WIDTH // 3, WIDTH // 3, WIDTH // 3, 10,
    MAGENTA, True)
pygame.display.flip()

def main():
    running = True
    clock = pygame.time.Clock()
    
    # Divide window into 9 sections and draw a different pattern and color
    # in each section.
    
    while running:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                # save the screen to a file
                pygame.image.save(window, "AbstractGridGo.png")
                running = False
        
    pygame.display.flip()
    clock.tick(10)

    pygame.quit()
    
if __name__ == "__main__":
    main()
