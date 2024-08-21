import os
import random
import sys

def overwrite_file(file_path, passes, pattern):
    file_size = os.path.getsize(file_path)
    
    with open(file_path, "wb") as f:
        for p in range(passes):
            if pattern == "random":
                data = bytearray(random.getrandbits(8) for _ in range(file_size))
            elif pattern == "zeros":
                data = bytearray(0 for _ in range(file_size))
            elif pattern == "custom":
                byte_value = int(input("Enter a custom byte value (0-255): "))
                data = bytearray(byte_value for _ in range(file_size))
            else:
                raise ValueError("Invalid pattern.")
            
            f.write(data)
            f.flush()
            sys.stdout.write(f"\rPass {p + 1}/{passes} completed.")
            sys.stdout.flush()
        print("\nFile overwritten successfully.")

def secure_delete(file_path, passes=3, pattern="random"):
    if not os.path.exists(file_path):
        print(f"Error: File '{file_path}' does not exist.")
        return
    
    confirm = input(f"Are you sure you want to securely delete '{file_path}'? (yes/no): ")
    if confirm.lower() != "yes":
        print("Operation cancelled.")
        return
    
    try:
        # Overwrite the file multiple times with the chosen pattern
        overwrite_file(file_path, passes, pattern)
        
        # Delete the file
        os.remove(file_path)
        print(f"File '{file_path}' securely deleted.")
    except Exception as e:
        print(f"Error securely deleting file '{file_path}': {e}")

# Example usage
if __name__ == "__main__":
    file_path = input("Enter the file with extension: ")
    
    # Optional: Create a test file with some content
    with open(file_path, "w") as f:
        f.write("This is a test file.")
    
    # Select the number of overwrite passes
    passes = int(input("Enter the number of overwrite passes (default is 3): ") or 3)
    
    # Choose the overwriting pattern
    pattern = input("Choose an overwrite pattern (random/zeros/custom): ").lower() or "random"
    
    # Securely delete the file
    secure_delete(file_path, passes, pattern)
