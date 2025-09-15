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