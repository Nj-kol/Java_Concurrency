package com.njkol.callable;

import java.util.concurrent.Callable;

public class FactorialTask implements Callable<Integer> {
	
    int number;

    public FactorialTask(int number) {
        this.number = number;
    }

    public Integer call() throws InvalidParamaterException {
        int fact=1;
        if(number < 0)
            throw new InvalidParamaterException("Number must be positive");

        for(int count=number;count>1;count--){
            fact=fact * count;
        }

        return fact;
    }

    private class InvalidParamaterException extends Exception {
   
		private static final long serialVersionUID = 132142428093629024L;

		public InvalidParamaterException(String message) {
            super(message);
        }
    }
}

