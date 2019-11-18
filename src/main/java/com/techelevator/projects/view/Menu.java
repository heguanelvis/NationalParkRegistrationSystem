package com.techelevator.projects.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Menu {

	private PrintWriter out;
	private Scanner in;

	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}

	public Object getUserInput() {
		return in.nextLine().trim();
	}

	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}

	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();

		if (options[options.length - 1] instanceof java.lang.String) {
			String quitOption = userInput.trim().toUpperCase();
			String lastOption = (String) options[options.length - 1];

			if (lastOption.equals("Quit") && quitOption.equals("Q")) {
				choice = lastOption;
			} else if (lastOption.equals("Quit") && userInput.equals(String.valueOf(options.length))) {
				userInput = null;
				out.println("\n*** " + options.length + " is not a valid option ***\n");
				return choice;
			}
		}

		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch (NumberFormatException e) {

		}

		if (choice == null) {
			out.println("\n*** " + userInput + " is not a valid option ***\n");
		}

		return choice;
	}

	private void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			if (options[i] == "Quit") {
				out.println("Q) " + options[i]);
			} else {
				int optionNum = i + 1;
				out.println(optionNum + ") " + options[i]);
			}
		}
		out.print("\nPlease choose an option >>> ");
		out.flush();
	}

}
