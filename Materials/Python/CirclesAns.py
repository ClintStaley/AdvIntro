# Create a Pygame animation of a set of circles with random sizes, color,
# velocity, and position.

# This is a buggy version resulting from a bit of vibe coding.  Stopped vibe
# coding when it wasn't making good progress.  Now it's our turn to fix it.

import pygame
import random
import sys
from collections import namedtuple

pygame.init()

window_width = 800
window_height = 600
window = pygame.display.set_mode((window_width, window_height))
pygame.display.set_caption("Circles")
clock = pygame.time.Clock()

# Define the named tuples
Coord = namedtuple('Coord', ['x', 'y'])
Circle = namedtuple('Circle', ['radius', 'color', 'velocity', 'position'])

# Generate a random circle with a random radius, color, velocity, and position.
# The circle should be a Circle named tuple with radius, color, velocity, and 
# position.
# The radius should be a random integer between 10 and 100.
# The color should be a random color.
# The velocity should be a random velocity between -10 and 10 for both x and y.
# The position should be a random position within the window, accounting 
# for the radius of the circle.
# Return the circle as a Circle named tuple.
def generate_circle():
    radius = random.randint(10, 100)
    color = (random.randint(0, 255), random.randint(0, 255), 
        random.randint(0, 255)) # Generate a random color   
    velocity_x = random.randint(-10, 10)
    velocity_y = random.randint(-10, 10)
    velocity = Coord(velocity_x, velocity_y)
    position = Coord(random.randint(0, window_width - radius), 
        random.randint(0, window_height - radius))
    return Circle(radius, color, velocity, position)

def generate_circles(num_circles):
    circles = []
    for i in range(num_circles):
        circles.append(generate_circle())
    return circles

def draw_circles(circles):
    for circle in circles:
        pygame.draw.circle(window, circle.color, 
            (circle.position.x, circle.position.y), circle.radius)

def move_circles(circles):
    updated_circles = []
    for circle in circles:
        # Update position
        new_x = circle.position.x + circle.velocity.x
        new_y = circle.position.y + circle.velocity.y
        new_velocity = circle.velocity
        
        # Check for boundary collisions and reverse velocity if needed
        if (new_x < circle.radius or 
            new_x > window_width - circle.radius):
            new_velocity = Coord(-new_velocity.x, new_velocity.y)
        if (new_y < circle.radius or 
            new_y > window_height - circle.radius):
            new_velocity = Coord(new_velocity.x, -new_velocity.y)
        
        # Clamp position to valid range
        new_x = max(circle.radius, min(new_x, window_width - circle.radius))
        new_y = max(circle.radius, min(new_y, window_height - circle.radius))
        new_position = Coord(new_x, new_y)
        
        # Create new Circle with updated position and velocity
        updated_circles.append(Circle(circle.radius, circle.color,
            new_velocity, new_position))
    return updated_circles
        
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