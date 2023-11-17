# Huffman Coding Project

## About the Project

The Huffman coding project is a Java application designed to encode and decode text data using the Huffman algorithm and obtain information about the encoded file. The Huffman algorithm is a data compression method based on the construction of an optimal prefix binary tree.

## Project Structure

The project is divided into several components:

1. **`Huffman` (Main Class)**
   - The main application class containing the main method for execution.

2. **`BitInputStream` and `BitOutputStream`**
   - Classes for convenient input and output of bit data.

3. **`Node`**
   - A class representing a node in the coding tree.

4. **`FileUtility`**
   - A utility class with useful functions.

5. **`FileWriter`**
   - A class for writing encoded and decoded data to files.

6. **`FileReader`**
   - A class for reading files and obtaining information about the compressed file size.

7. **`HuffmanEncoder`**
   - Сlass serves as a utility for generating Huffman codes, constructing Huffman trees, and displaying Huffman codes for individual characters.

## Running the Project

The project supports the following modes:

- **`encode`** - Encode a file.
- **`decode`** - Decode a compressed file.
- **`info`** - Get information about compressed file.

Example runs:

```bash
java Huffman encode input.txt
java Huffman decode input.ark
java Huffman info input.ark
```

## Example Input File

This is an example of input file ![input.txt]("Example files"/input.png).

This is an info about the encoded input.arc ![info]("Example files"/info.png).
