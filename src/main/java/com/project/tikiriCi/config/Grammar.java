package com.project.tikiriCi.config;

import java.util.Arrays;

import com.project.tikiriCi.parser.Derivation;
import com.project.tikiriCi.parser.NonTerminal;

public class Grammar {
   public static NonTerminal START = new NonTerminal("start", Arrays.asList(
      new Derivation()
   
   ));

   public static NonTerminal FUNCTION = new NonTerminal("function", null) 
}
