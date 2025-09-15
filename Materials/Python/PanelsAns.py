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
palette = [
    (255, 0, 0), #red,
    (0, 255, 0), #green,
    (0, 0, 255), #blue,
    (255, 255, 0), #yellow,
    (255, 0, 255), #magenta,
    (0, 255, 255), #cyan,
    (255, 255, 255)] #white

def desaturate(palette):
    for i in range(len(palette)):
        # move each color closer to 128 by 10%
        # palette[i] = tuple(128 + int((c-128) * 0.9) for c in palette[i])
        for c in range(len(palette[i])):
            palette[i][c] = 128 + int((palette[i][c]-128) * 0.9)
    return palette

# Increase distance of each color from 128 by 10%, clamping at 0 and 255
def saturate(palette):
    for i in range(len(palette)):
        new_color = []
        for c in range(len(palette[i])):
            new_color.append(max(0, min(255, 128 + int((palette[i][c]-128) * 1.1))))
        palette[i] = new_color
    return palette

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
                        if y > 100:
                            window.set_at((x, y), palette[2])  # blue
                        else:
                            window.set_at((x, y), palette[3])  # yellow
                    else:
                        if y > 450:
                            window.set_at((x, y), palette[3])  # yellow
                        else:
                            window.set_at((x, y), palette[5])  # cyan
                else:
                    if (x > 500 and x < 600 and y > 300 and y < 400):
                        window.set_at((x, y), palette[6])  # white
                    else:
                        window.set_at((x, y), palette[4])  # magenta

# Draw initial pattern
draw_pattern(window, window_width, window_height, palette)

while True:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            pygame.quit()
            sys.exit()
        elif event.type == pygame.KEYDOWN:
            if event.key == pygame.K_1:
                saturate(palette)
                draw_pattern(window, window_width, window_height, palette)
            elif event.key == pygame.K_2:
                desaturate(palette)
                draw_pattern(window, window_width, window_height, palette)
            elif event.key == pygame.K_ESCAPE:
                # Write to file Panels.png
                pygame.image.save(window, "Panels.png")
                pygame.quit()
                sys.exit()

    pygame.display.update()
    clock.tick(10)
