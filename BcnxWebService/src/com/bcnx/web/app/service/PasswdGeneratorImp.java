package com.bcnx.web.app.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswdGeneratorImp implements PasswdGenerator {
	
	// we keep our data in lists. Arrays would suffice as data never changes though.
	private final static List<Character> LOWER_CAPS, UPPER_CAPS, DIGITS, SPECIALS;

	// stores all templates
	private List<Template> templateList = new ArrayList<Template>();

	// indicates if we should shuffle the password
	private boolean doShuffle;

	/**
	 * Factory method to create our builder.
	 *
	 * @return New PasswordBuilder instance.
	 */
	public static PasswdGeneratorImp builder() {
	    return new PasswdGeneratorImp();
	}

	/**
	 * Adds lowercase letters to password.
	 *
	 * @param count Number of lowercase letters to add.
	 * @return This instance.
	 */
	public PasswdGeneratorImp lowercase(int count) {
	    templateList.add(new Template(LOWER_CAPS, count));
	    return this;
	}

	public PasswdGeneratorImp uppercase(int count) {
	    templateList.add(new Template(UPPER_CAPS, count));
	    return this;
	}

	public PasswdGeneratorImp digits(int count) {
	    templateList.add(new Template(DIGITS, count));
	    return this;
	}

	public PasswdGeneratorImp specials(int count) {
	    templateList.add(new Template(SPECIALS, count));
	    return this;
	}

	/**
	 * Indicates that the password will be shuffled once
	 * it's been generated.
	 *
	 * @return This instance.
	 */
	public PasswdGeneratorImp shuffle() {
	    doShuffle = true;
	    return this;
	}

	/**
	 * Builds the password.
	 *
	 * @return The password.
	 */
	public String build() {
	    // we'll use StringBuilder
	    StringBuilder passwordBuilder = new StringBuilder();
	    List<Character> characters = new ArrayList<Character>();

	    // we want just one list containing all the characters
	    for (Template template : templateList) {
	        characters.addAll(template.take());
	    }

	    // shuffle it if user wanted that
	    if (doShuffle)
	        Collections.shuffle(characters);

	    // can't append List<Character> or Character[], so
	    // we do it one at the time
	    for (char chr : characters) {
	        passwordBuilder.append(chr);
	    }

	    return passwordBuilder.toString();
	}

	// initialize statics
	static {
	    LOWER_CAPS = new ArrayList<Character>(26);
	    UPPER_CAPS = new ArrayList<Character>(26);
	    for (int i = 0; i < 26; i++) {
	        LOWER_CAPS.add((char) (i + 'a'));
	        UPPER_CAPS.add((char) (i + 'A'));
	    }

	    DIGITS = new ArrayList<Character>(10);
	    for (int i = 0; i < 10; i++) {
	        DIGITS.add((char) (i + '0'));
	    }

	    // add special characters. Note than other
	    // than @, these are in ASCII range 33-43
	    // so we could have used the loop as well
	    SPECIALS = new ArrayList<Character>() {
			private static final long serialVersionUID = 1L;

		{
	        add('!');
	        add('@');
	        add('#');
	        add('$');
	        add('%');
	        add('^');
	        add('&');
	        add('(');
	        add(')');
	        add('*');
	        add('+');
	    }};
	}

	@Override
	public String generate() {
		this.lowercase(2).uppercase(2).specials(2).digits(2).shuffle();
		return this.build();
	}
}
