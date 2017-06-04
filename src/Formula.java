import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

public class Formula {
	private char variable;
	private String formula;
	private String postfix;
	private Set<Integer> result;
	
	private final HashMap<String, Integer> opPriority = new HashMap<String, Integer>(){{
		put("*", 3);
		put("/", 3);
		put("+", 2);
		put("-", 2);
		put("(", 1);
	}};
	//{'+','-','/','*', '('}
	private final Set<String> operators = opPriority.keySet(); 	//!DON'T NEED THE ) THOUGH
//	private final ArrayList<String> operators = new ArrayList<>(Arrays.asList("+","-","/","*"));
	
	public Formula(char variable, String formula) {
		this.variable = variable;
		this.formula = formula;
		this.result = new LinkedHashSet<Integer>();
		process();
	}
	
	protected Formula(String formula){
		this.formula = formula;
	}
	
	public void process(){
		expandedForm();
		infixToPostfix();
		generateOutput();
		refineSol();
	}
	
	//Expands simplifed multiplication --> e.g. 5x = ( 5 * x )
	//!!!Doesn't deal with 5(..) yet
	public void expandedForm(){
		StringBuilder sb = new StringBuilder(new String());
		String[] seperated = formula.split(" ");
		for(String s : seperated){
			if(!s.equals(String.valueOf(variable)) && s.contains(String.valueOf(variable))){ 	//handles when concat x e.g. 5x and ignores if x is by itself
				char var = s.charAt(s.length()-1);
				String num = s.substring(0, s.length()-1);
				sb.append("( " + num + " * " + var + " ) ");
			}else{
				sb.append(s + " ");
			}
		}
		formula = sb.toString();
	}
	
	//Make static - part of class not instance
	public void infixToPostfix(){
		Stack<String> opStack = new Stack<String>();		//operand stack
		List<String> output = new LinkedList<String>();
		
		String[] tokens = formula.split(" ");
		for(String token : tokens){
			//check if number or variable
			if(token.chars().allMatch(Character::isDigit)|| token.equals(String.valueOf(variable))){
				output.add(token);
			}else if(token.equals("(")){
				opStack.push("(");
			}else if(token.equals(")")){
				while(!opStack.empty() && !opStack.peek().equals("(")){
					output.add(opStack.pop());
				}
				if(!opStack.empty()){
					opStack.pop();
				}
			}else if(operators.contains(token)){
				while(!opStack.empty() && opPriority.get(token) <= opPriority.get(opStack.peek())){		//BIDMAS check
					output.add(opStack.pop());
				}
				opStack.push(token);
			}
		}
		
		while(!opStack.isEmpty()){			//remaining stack after gone trough tokens
			output.add(opStack.pop());
		}
		
		StringBuilder sb = new StringBuilder();
		for(String s : output){
			sb.append(s + " ");
		}
		postfix = sb.toString();
	}
	
	public void generateOutput(){
		//change to array after for faster?
		Queue<String> queue = new LinkedBlockingQueue<String>();
		Stack<Integer> stack = new Stack<Integer>();
		Set<Integer> output = new LinkedHashSet<Integer>();
		int range = 0;
		int x = 0;
		
		for(;;){
			if(range < 100){
				int op1;
				int op2;
				
				for(String s : postfix.split(" ")){
					queue.add(s);
				}
				
				while(!queue.isEmpty()){
					while(!operators.contains(queue.peek())){
						if(queue.peek().equals(String.valueOf(variable))){
							queue.poll();
							stack.push(x);
						}else{
							stack.push(Integer.valueOf(queue.poll()));
						}
					}
					op1 = stack.pop();
					op2 = stack.pop();
					
					switch(queue.poll()){
						case "+":
							stack.push(op1+op2);
							break;
						case "-":
							stack.push(op2-op1);
							break;
						case "*":
							stack.push(op1*op2);
							break;
						case "/":					//Will floor if decimal
							stack.push(op2/op1);
							break;
						default:
							//!!!!!!!!! CLEAN UP
							System.err.println("SOMETHING WENT WRONG IN OPERATION EVALUATION");
					}
				}
				range = stack.peek();
				output.add(stack.pop());
				x++;
			}else{
				break;
			}
			result = output;
		}
	}
	
	public String getPostfix(){
		return postfix;
	}
	
	public Set<Integer> getResult(){
		return result;
	}
	
	public void refineSol(){
		for(int i: result){
			if(i >=100){
				result.remove(i);
			}
		}
	}
	
	public void printSol(){
		int j = 0;
		
		for(int i : result){
			System.out.println(j + "\t" +i);
			j++;
		}
	}
}
