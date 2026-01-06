# PygameDemo.py - a simple Pygame demo
# Set up a 600x800 window, fill it with two random rectangles, one red and one blue.
# Event loop redoes the drawing on R key, and quits on Q key.

import pygame   # Standard library for doing graphics, animations, etc.
import random   # Standard library for doing random numbers
import sys      # Standard library for doing system stuff

pygame.init()   # Initialize Pygame -- required for all Pygame programs

window_width = 800
window_height = 600

# Set up the window, an object that can be used later to draw on.
window = pygame.display.set_mode((window_width, window_height))
pygame.display.set_caption("Pygame Demo")  # Set the window caption

def draw_pattern(window, window_width, window_height):
    window.fill((0, 0, 0))  # Fill the window with black
    
    # Draw two rectangles of random origin and size, one red and one blue.
    pygame.draw.rect(window, (255, 0, 0), 
        (random.randint(0, window_width),
        random.randint(0, window_height), 
        random.randint(10, 100), 
        random.randint(10, 100)))
    pygame.draw.rect(window, (0, 0, 255),  (
        random.randint(0, window_width),
        random.randint(0, window_height), 
        random.randint(10, 100), 
        random.randint(10, 100)))
    
    # Mandatory step to update the window or the display will not show the changes.
    pygame.display.flip()  # or pygame.display.update()

draw_pattern(window, window_width, window_height)

while True:
    # Event loop -- check for events and handle them. Each event is an object
    # representing, e.g. a mouse click, a key press, etc.  Events must occur
    # with the Pygame window *in focus* or they won't show up here.
    for event in pygame.event.get():
        if event.type == pygame.QUIT: # If the user clicks the close button..
            pygame.quit()             # Shut down Pygame -- required.
            sys.exit()                # Shut down the program
        elif event.type == pygame.KEYDOWN: # If the user presses a key..
            if event.key == pygame.K_r:
                draw_pattern(window, window_width, window_height)
            elif event.key == pygame.K_q:  # If the user presses the Q key..
                pygame.quit()
                sys.exit()
    
    # pygame.display.flip()  good idea, though optional here.