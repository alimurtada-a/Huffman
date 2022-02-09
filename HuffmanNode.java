// Alimurtada Al-Shimari
// This class stores a characters int value and how frequently it apears in a txt file.
// if it is noit a leaf node it will store a path to the character based on that characters binary code

public class HuffmanNode implements Comparable<HuffmanNode> {
   //frequency of char in file
   public int frequency;
   //characters int value
   public int character;
   //stores node for "0" bit
   public HuffmanNode left;
   //stores node for "1" bit
   public HuffmanNode right;

   //creates node that stores character and frequency
   public HuffmanNode(int frequency, int character) {
      this(frequency, character, null, null);
   }

   //creates noce that sotres path to leaf node
   public HuffmanNode(int frequency, int character, HuffmanNode left,
   HuffmanNode right) {
      this.frequency = frequency;
      this.character = character;
      this.left = left;
      this.right = right;
   }

   //pre: recives node of same type
   //post: compares frequency of characters returns negative if frequrency is smaller
   //      0 if they are the same and positive if greater
   public int compareTo(HuffmanNode other) {
      return this.frequency - other.frequency;
   }
}