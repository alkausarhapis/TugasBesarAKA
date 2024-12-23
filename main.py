import tkinter as tk
from tkinter import messagebox
import time

def count_ways_recursive(n, memo=None):
    if memo is None:
        memo = {}
    if n == 0:
        return 1
    if n < 0:
        return 0
    if n in memo:
        return memo[n]
    memo[n] = count_ways_recursive(n - 1, memo) + count_ways_recursive(n - 2, memo)
    return memo[n]

def count_ways_iterative(n):
    if n == 0:
        return 1
    if n == 1:
        return 1
    a, b = 1, 1
    for _ in range(2, n + 1):
        a, b = b, a + b
    return b

def compare_algorithms():
    try:
        n = int(entry_steps.get())
        if n < 0:
            messagebox.showerror("Invalid Input", "Number of steps must be non-negative.")
            return

        # Rekursif
        start_recursive = time.perf_counter()
        recursive_result = count_ways_recursive(n)
        end_recursive = time.perf_counter()
        recursive_time = (end_recursive - start_recursive) * 1000 

        # Iteratif
        start_iterative = time.perf_counter()
        iterative_result = count_ways_iterative(n)
        end_iterative = time.perf_counter()
        iterative_time = (end_iterative - start_iterative) * 1000 

        # Update hasil di UI
        label_recursive_result.config(text=f"Result: {recursive_result}")
        label_recursive_time.config(text=f"Time: {recursive_time:.4f} ms")

        label_iterative_result.config(text=f"Result: {iterative_result}")
        label_iterative_time.config(text=f"Time: {iterative_time:.4f} ms")

    except ValueError:
        messagebox.showerror("Invalid Input", "Please enter a valid number.")

root = tk.Tk()
root.title("Staircase Algorithm Comparison")

frame_input = tk.Frame(root, padx=10, pady=10)
frame_input.pack()

label_input = tk.Label(frame_input, text="Enter number of steps (N):")
label_input.grid(row=0, column=0, padx=5, pady=5)

entry_steps = tk.Entry(frame_input, width=10)
entry_steps.grid(row=0, column=1, padx=5, pady=5)

button_submit = tk.Button(frame_input, text="Compare", command=compare_algorithms)
button_submit.grid(row=0, column=2, padx=5, pady=5)

frame_recursive = tk.LabelFrame(root, text="Recursive Algorithm", padx=10, pady=10)
frame_recursive.pack(padx=10, pady=10, fill="both")

label_recursive_result = tk.Label(frame_recursive, text="Result: -")
label_recursive_result.pack(anchor="w")

label_recursive_time = tk.Label(frame_recursive, text="Time: - ms")
label_recursive_time.pack(anchor="w")

frame_iterative = tk.LabelFrame(root, text="Iterative Algorithm", padx=10, pady=10)
frame_iterative.pack(padx=10, pady=10, fill="both")

label_iterative_result = tk.Label(frame_iterative, text="Result: -")
label_iterative_result.pack(anchor="w")

label_iterative_time = tk.Label(frame_iterative, text="Time: - ms")
label_iterative_time.pack(anchor="w")

root.mainloop()
