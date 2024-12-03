package org.example;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {
  public static void main(String[] args){
    Facade facade = new Facade();
    try{
      System.out.println(facade.getAttributeValueFromJson("https://api.chucknorris.iko/jokes/random",
              ""));
    } catch (Exception exception){
      exception.printStackTrace();
    }

  }
}