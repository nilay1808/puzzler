package puzzler;

import java.util.ArrayList;
import java.util.Stack;

public class puzzler {

	static String input = "";
	static String answer = "";

	public static void main(String[] args) throws Exception {

		// Give input here
		input = "2351";

		// Give answer here
		answer = "11";

		ArrayList<String> allEqns = eqnBuilder(input);

		ArrayList<Integer> allOutcomes = postfixEval(allEqns);

		int index = allEqns.size() - allOutcomes.size();

		if (findAnswerIndex(allOutcomes, Integer.parseInt(answer)) == Integer.MAX_VALUE)
			System.out.println("ANSWER NOT FOUND");
		else
			index += findAnswerIndex(allOutcomes, Integer.parseInt(answer));

		System.out.println("The correct equation is " + allEqns.get(index));

		System.out.println(evaluate(allEqns.get(index)));
	}
	
	/*
	 * Pseudo code- 
	 * take in input of n numbers
	 * take in desired answer 
	 * build a list with all possible equations 
	 * take all complete equations and evaluate their solns 
	 * compare solns with answer 
	 * return the equation that gives that answer
	 */

	public static ArrayList<String> eqnBuilder(String init) {
		
		ArrayList<String> outcomes = new ArrayList<String>();
		
		outcomes.add(init);
		
		int elements = numElements(input);
		
		for (int i = 0; i < (elements - 5) / 4 + 1; i++) {
			String s = outcomes.get(i);
			outcomes.add(s + "+");
			outcomes.add(s + "-");
			outcomes.add(s + "*");
			outcomes.add(s + "/");
		}
		
		return outcomes;
	}

	public static ArrayList<Integer> postfixEval(ArrayList<String> list) throws Exception {
		
		ArrayList<Integer> solutions = new ArrayList<Integer>();
		
		for (int i = (list.size() - 5) / 4 + 1; i < list.size(); i++) {
			solutions.add(evaluate(list.get(i)));
		}
		
		return solutions;
	}

	public static int evaluate(String equation) throws Exception {
		
		Stack<Integer> stack = new Stack<Integer>();
		
		for (int j = 0; j < equation.length(); j++) {
			
			String ele = equation.charAt(j) + "";
			
			if (!isOperand(ele)) {
				stack.push(Integer.parseInt(ele));
			} 
			else {
				if (ele.equals("/")) {
					int a = stack.pop();
					int b = stack.pop();
					if (b % a == 0)
						stack.push(b / a);
					else
						stack.push(Integer.MAX_VALUE);
				} 
				else {
					int ans = operate(stack.pop(), stack.pop(), ele);
					if (ans == Integer.MIN_VALUE)
						throw new Exception("Error");
					else {
						stack.push(ans);
					}
				}
			}
		}
		
		return stack.peek();
	}

	public static int findAnswerIndex(ArrayList<Integer> list, int ans) {
		int index = Integer.MAX_VALUE;

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == ans)
				return i;
		}

		return index;
	}

	public static boolean isOperand(String s) {
		return (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/"));
	}

	public static int operate(int a, int b, String s) {
		int ans = Integer.MIN_VALUE;
		if (s.equals("+")) {
			return b + a;
		} else if (s.equals("-")) {
			return b - a;
		} else if (s.equals("*")) {
			return b * a;
		} else if (s.equals("/")) {
			return b / a;
		} else
			return ans;
	}

	public static int numElements(String init) {
		int elements = 0;
		for (int i = 1; i <= init.length() - 1; i++) {
			int power = (int) Math.pow(4, i);
			elements = elements + power;
		}
		return elements;
	}
}
