import numpy as np
import matplotlib.pyplot as plt

# 1. Create NumPy arrays of various shapes
arr_1d = np.array([1, 2, 3])
arr_2d = np.array([[1, 2, 3], [4, 5, 6]])

arr_3d = np.arange(24).reshape((4, 2, 3))  # 3D array with shape (4, 2, 3)

print("1D Array:\n", arr_1d)
print("2D Array:\n", arr_2d)
print("3D Array:\n", arr_3d)

# 2. Demonstrate broadcasting
# Direct operations
print(f"Elementwise Addition:\n{arr_1d + arr_1d}\n{arr_2d + arr_3d[:2]}")
print(f"Elementwise Multiplication:\n{arr_2d*arr_2d}")

# Broadcasting
print(f"Broadcast Addition 2d to 1d:\n{arr_2d + arr_1d}")
print(f"Broadcast Addition 2d to 3d:\n{arr_2d + arr_3d}")
print(f"Reshaping to (1,2,3):\n{arr_2d.reshape(1, 2, 3)}")
print(f"Reshaping to (2,1,3):\n{arr_2d.reshape(2, 1, 3)}")
print(f"Reshaped add giving (2,2,3):\n"
    f"{arr_2d.reshape(1, 2, 3) + arr_2d.reshape(2, 1, 3)}")

# 3. Simple linear algebra: matrix multiplication
# (2x3) dot (3x2) to get a (2x2) matrix
mat_a = np.array([[1, 2, 3], [4, 5, 6]])
mat_b = np.array([[7, 8], [9, 10], [11, 12]])

product = mat_a.dot(mat_b)
print("Matrix Multiplication (mat_a x mat_b):\n", product)

# 4. Save and load arrays to/from text files
# Save arr_2d to output.txt
# Can save higher dimensional array to numpy file: np.save("arr_3d.npy", arr_3d)
np.savetxt("output.txt", arr_2d, fmt="%d")
new_arr_2d = np.loadtxt("output.txt")

# Repeat new_arr_2d across 10 times and down 5 times
new_arr_2d = np.tile(new_arr_2d, (5, 10))
# 5. Use matplotlib to display a NumPy array
# We'll display arr_2d as an image for illustration
plt.imshow(new_arr_2d, cmap="viridis", interpolation="nearest")
plt.colorbar(label="Value Scale")
plt.title("arr_2d Visualization")
plt.show()

# Create an array of 5 x/y coordinates with values randomly between -10 and 10
coords = np.random.rand(5, 2) * 20 - 10
# Create a 2x2 matrix that rotates 45 degrees ccw and scales by 1.414
rotation_matrix = np.array([[1, -1], [1, 1]])
# Rotate and scale the coordinates  
rotated_coords = coords.dot(rotation_matrix)
# Print the rotated coordinates
print("Rotated Coordinates:\n", rotated_coords)


# Create a 3D array of z values with values randomly between -10 and 10
z = np.random.rand(100, 100) * 20 - 10
