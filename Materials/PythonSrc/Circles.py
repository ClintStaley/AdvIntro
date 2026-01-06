# Create a Pygame animation of a set of circles with random sizes, color,
# velocity, and position.

# This is a buggy version resulting from a bit of vibe coding.  Stopped vibe
# coding when it wasn't making good progress.  Now it's our turn to fix it.

import pygame
import random
import sys

pygame.init()

window_width = 800
window_height = 600
window = pygame.display.set_mode((window_width, window_height))
pygame.display.set_caption("Circles")
clock = pygame.time.Clock()

# Generate a random circle with a random size, color, velocity, and position.
# The circle should be a tuple of (size, color, velocity, position).
# The size should be a random integer between 10 and 100.
# The color should be a random color.
# The velocity should be a random velocity between -10 and 10 for both x and y.
# The position should be a random position within the window, accounting 
# for the size of the circle.
# Return the circle as a tuple of (size, color, velocity, position).
def generate_circle():
    size = random.randint(10, 100)
    color = (random.randint(0, 255), random.randint(0, 255), random.randint(0, 255))
    velocity = [random.randint(-10, 10), random.randint(-10, 10)]
    position = [random.randint(size, window_width - size), 
        random.randint(size, window_height - size)]
    return [size, color, velocity, position]

def generate_circles(num_circles):
    circles = []
    for i in range(num_circles):
        circles.append(generate_circle())
    return circles

def draw_circles(circles):
    for circle in circles:
        pygame.draw.circle(window, circle[1], circle[3], circle[0])

def move_circles(circles):
    for circle in circles:
        circle[3] = (circle[3][0] + circle[2][0], circle[3][1] + circle[2][1])
        if circle[3][0] < 0 or circle[3][0] > window_width - circle[0]:
            circle[2][0] = -circle[2][0]
        if circle[3][1] < 0 or circle[3][1] > window_height - circle[0]:
            circle[2][1] = -circle[2][1]
    return circles
        
def main():
    circles = generate_circles(10)
    while True:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()
        circles = move_circles(circles)
        window.fill((0, 0, 0))  # Clear the screen
        draw_circles(circles)
        
        pygame.display.flip()
        clock.tick(10)

if __name__ == "__main__":
    main()