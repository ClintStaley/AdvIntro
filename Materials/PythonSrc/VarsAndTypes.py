# Python Types and Operations Overview
# Demonstrates core data types, indexing, and operations

# Basic variable types
num_int = 4200
num_float = 3.14159
text = "Hello, Python!"
flag = True

# References - all variables are references to objects
second_ref = num_int # Two references to the same int object (4200)
third_ref = 4000  # (If we assign 4200, it will cleverly reuse the same object)
third_ref += 200  # But, now third_ref refers to 4200, **as a different object**

if second_ref is num_int: # "shallow" comparison: do both refer to the same object?
    print("second_ref and num_int refer to the same object.")
if third_ref is not num_int:
    print("But third_ref does not refer to the same object.")
if second_ref == num_int and third_ref == num_int:  # "deep" comparison of targets
    print("Still, all have the same value in their (two different) targets.")

# Collection types - mutable vs immutable
numbers = [1, 2, 3, 4, 5]           # list (mutable)
coordinates = (10.5, 20.3)          # tuple (immutable)
unique_items = {1, 2, 3, 2, 1}      # set (mutable, unordered, no duplicates)
config = {"debug": True, "port": 8080}  # dict (mutable)
print(f"Initial state - numbers: {numbers}, coordinates: {coordinates}" 
      "unique_items: {unique_items}, config: {config}")

# Indexing and slicing (works on sequences: strings, lists, tuples)
print(f"First char: '{text[0]}', last char: '{text[-1]}'")
print(f"Slice: '{text[7:13]}', every 2nd: '{text[::2]}'")
print(f"List slice: {numbers[1:4]}, reversed: {numbers[::-1]}")
# print just Hello from text

# Math operations and type interactions
result = num_int + num_float         # int + float -> float
power = num_int ** 2                 # exponentiation
division = 17 // 5                   # floor division (3)
remainder = 17 % 5                   # modulo (2)

# String operations and formatting
greeting = text.upper().replace("PYTHON", "World")
formatted = f"Result: {result:.2f}, Power: {power}"
print(greeting)
print(formatted)

# List operations (mutation)
numbers.append(6)                    # mutates (modifies) original list
doubled = [x * 2 for x in numbers]  # list comprehension

# Set operations
more_numbers = {4, 5, 6, 7}
intersection = unique_items & more_numbers  # {1, 2, 3} & {4, 5, 6, 7} = set()
union = unique_items | more_numbers         # union of sets
print(f"Set operations - intersection: {intersection}, union: {union}")

# Dictionary access and methods
port = config.get("port", 3000)     # safe access with default
config["version"] = "1.0"           # adding new key-value pair
print(f"Config: {config}, Host: {config.get('host', 'localhost')}")

# Type checking and conversions
print(f"Types: {type(num_int).__name__}, {type(coordinates).__name__}")
str_number = str(num_int)           # "42"
back_to_int = int(str_number)       # 42

# Boolean operations and truthiness
empty_list = []
print(f"Truthiness: {bool(numbers)} vs {bool(empty_list)}")  # True vs False

# Multiple assignment and unpacking
x, y = coordinates                   # tuple unpacking
first, *rest, last = numbers        # extended unpacking: first=1, rest=[2,3,4,5], last=6

print(f"Final state - numbers: {numbers}, doubled: {doubled}")
print(f"Unpacked: first={first}, rest={rest}, last={last}")