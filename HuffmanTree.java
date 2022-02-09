// Alimurtada Al-Shimari
// This program takes a text file and uses the Huffman algorithm to compress it down
// and shares the bits of the file for each character based on how frequent it exists in the file.

import java.util.*;
import java.io.*;

public class HuffmanTree {
   private HuffmanNode headRoot;

   //pre: all the frequencies of the characters in the file given are passed in
   //post: the characters from the file are saved so that each character will have a unique
   //      based on the frequency. The more the character exists in the file, the fewer bits.
   //     also, extra character will be added to the end to indicate the end of the file. it will
   //      not be printed
   public HuffmanTree(int[] count) {
      Queue<HuffmanNode> frequencyQueue = new PriorityQueue<>();
      for (int i = 0; i < count.length; i++) {
         if (count[i] > 0) {
            HuffmanNode characterLeafNodes = new HuffmanNode(count[i], i);
            frequencyQueue.add(characterLeafNodes);
         }
      }
      HuffmanNode eof = new HuffmanNode(1, count.length);
      frequencyQueue.add(eof);
      while (frequencyQueue.size() > 1) {
         HuffmanNode leftNode = frequencyQueue.remove();
         HuffmanNode rightNode = frequencyQueue.remove();
         HuffmanNode sum = new HuffmanNode(leftNode.frequency + rightNode.frequency, -1,
                                           leftNode, rightNode);
         frequencyQueue.add(sum);
      }
      headRoot = frequencyQueue.remove();
   }

    //pre:  a standard formated file will be passed in with pairs of lines with the
    //      character and the binary number
    //post: the characters are stored based off thier binary number,
    //      The more the character exists in the file, the fewer bits.
    //      also, extra character will be added to the end to indicate the end of the file. it will
    //      not be printed
     public HuffmanTree(Scanner input){
      while (input.hasNextLine()) {
         int charVal = Integer.parseInt(input.nextLine());
         String code = input.nextLine();
         headRoot = HuffmanTreeHelper(headRoot, charVal, 0, code);
      }
   }

   //pre: recives a file to write in
   //post: writes the characters and the binary number in a standard format, which looks like
   //      line pairs with the int value first and then its binary number
   public void write(PrintStream output) {
      writeHelper(output, headRoot, "");
   }

   //pre: recives a file to write in, the location of the chacters int value and the binary number
   //post: writes the characters int value and then the binary number
   private void writeHelper(PrintStream output, HuffmanNode node, String path) {
      if (node.left == null && node.right == null) {
         output.println(node.character);
         output.println(path);
      } else {
         writeHelper(output, node.left, path + "0");
         writeHelper(output, node.right, path + "1");
      }
   }

   //pre: recives the int value of the character the binary identity, the location of the character
   //     in the tree, as well as the bit
   //post: returms the correct assighnment of bits for the character by using the binary number
   private HuffmanNode HuffmanTreeHelper(HuffmanNode node, int charVal, int index, String code) {
      if (index == code.length()) {
         return new HuffmanNode(-1, charVal);
      }
      if (node == null) {
         node = new HuffmanNode(-1, -1);
      }
      if (code.charAt(index) == '0') {
         node.left = HuffmanTreeHelper(node.left, charVal, index + 1, code);
      } else {
         node.right = HuffmanTreeHelper(node.right, charVal, index + 1, code);
      }
      return node;
   }

   //pre: recives the binary number for an int, a file to write in and the int value
   //     The binary numbers are consistent thoruhott the Huffman program.
   //post: writes the characters based off the binary numbers passed in until it reaches the
   //      end of file indicator. It does not print the end of fil indocator
   public void decode(BitInputStream input, PrintStream output, int eof) {
      int charVal = 0;
      while (charVal != eof) {
         charVal = decodeHelper(input, headRoot);
         if (charVal != eof) {
            output.write(charVal);
         }
      }
   }

   //pre: recives a binary number of each unique character and its location to be returned
   //post: returns the characters int value based on the binary number passed in.
   private int decodeHelper(BitInputStream input, HuffmanNode node) {
      if (node.left == null && node.right == null) {
         return node.character;
      } else {
         int bitVal = input.readBit();
         if (bitVal == 0) {
            return decodeHelper(input, node.left);
         } else {
            return decodeHelper(input, node.right);
         }
      }
   }
}