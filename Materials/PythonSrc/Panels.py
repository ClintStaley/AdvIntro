# Use Pygame to create a panel of colors, by breaking the window into left/right
# portions, and then dividing those up and down, and dividing further
# left/right, and then repeating the process till there are 7 panels.

import pygame
import sys

pygame.init()

window_width = 800
window_height = 600
window = pygame.display.set_mode((window_width, window_height))
pygame.display.set_caption("Panels")

clock = pygame.time.Clock()

# list of colors to use
palette0 = [(255, 0, 0), #red,
          (0, 255, 0), #green,
          (0, 0, 255), #blue,
          (255, 255, 0), #yellow,
          (255, 0, 255), #magenta,
          (0, 255, 255), #cyan,
          (255, 255, 255)] #white

palette1 = [(255, 100, 100), # desaturated red,
          (100, 255, 100), # desaturated green,
          (100, 100, 255), # desaturated blue,
          (255, 255, 100), # desaturated yellow,
          (255, 100, 255), # desaturated magenta,
          (100, 255, 255), # desaturated cyan,
          (200, 200, 200)] # "desaturated" white

def draw_pattern(window, window_width, window_height, palette):
    """Draw the panel pattern by determining which panel each pixel belongs to."""
    window.fill((0, 0, 0))
    
    for x in range(0, window_width):
        for y in range(0, window_height):
            if x < 200:
                if y < 150:
                    window.set_at((x, y), palette[0])  # red
                else:
                    window.set_at((x, y), palette[1])  # green
            else:
                if y < 250:
                    if x < 350:
                        window.set_at((x, y), palette[2])  # blue
                    else:
                        if y > 150:
                            window.set_at((x, y), palette[3])  # yellow
                        else:
                            window.set_at((x, y), palette[5])  # cyan
                else:
                    if x > 600 and x < 700 and y > 400 and y < 500:
                        window.set_at((x, y), palette[6])  # white
                    else:
                        window.set_at((x, y), palette[4])  # magenta

# Draw initial pattern
draw_pattern(window, window_width, window_height, palette0)

while True:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            pygame.quit()
            sys.exit()
        elif event.type == pygame.KEYDOWN:
            if event.key == pygame.K_1:
                draw_pattern(window, window_width, window_height, palette0)
            elif event.key == pygame.K_2:
                draw_pattern(window, window_width, window_height, palette1)
            elif event.key == pygame.K_ESCAPE:
                pygame.quit()
                sys.exit()

    pygame.display.update()
    clock.tick(10)
