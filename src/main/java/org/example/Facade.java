package org.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Facade {
    String getAttributeValueFromJson(String urlString, String attributeName) throws IllegalArgumentException, IOException, ParseException {
      // Validate input
      if (urlString == null || attributeName == null) {
        throw new IllegalArgumentException("URL or attribute name cannot be null");
      }

      // Initialize connection
      HttpURLConnection con = null;
      try {
        URL url = new URL(urlString);
        con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        // Check HTTP response code
        int responseCode = con.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
          throw new IOException("HTTP error: " + responseCode);
        }

        // Read JSON response
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
          StringBuilder content = new StringBuilder();
          String inputLine;
          while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
          }

          // Parse JSON
          JSONParser parser = new JSONParser();
          Object parsedObject = parser.parse(content.toString());
          if (!(parsedObject instanceof JSONObject)) {
            throw new ParseException(ParseException.ERROR_UNEXPECTED_TOKEN, "Expected a JSON object");
          }
          JSONObject jsonObject = (JSONObject) parsedObject;

          // Extract attribute value
          if (!jsonObject.containsKey(attributeName)) {
            throw new IllegalArgumentException("Attribute not found in JSON: " + attributeName);
          }
          return (String) jsonObject.get(attributeName);
        }
      } finally {
        if (con != null) {
          con.disconnect();
        }
      }
    }
  }

