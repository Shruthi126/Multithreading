package udemy.classes.deadlock;

public class BankAccount {
	
	private int balance=1000;
	private String owner;
	
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public void withdraw(int amount){
		balance= balance-amount;
	}
	public void deposit(int amount){
		balance= balance+amount;
	}
	public static void transfer(BankAccount acc1, BankAccount acc2,int tamount){
		acc1.withdraw(tamount);
		acc2.deposit(tamount);
	}
	
}
