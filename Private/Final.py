import numpy as np

array = np.array([[1,2,3], [4,5,6]])
print(array.shape)
array = np.maximum(np.minimum(array, 4), 2)
print(array)
array = np.dot(array, array.T)  # (transpose)
print(array)                    # Should be 2x2
