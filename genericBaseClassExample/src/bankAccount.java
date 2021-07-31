public class bankAccount {

    float balance;
    int accountNumber;
    String accountName;

    void setBalance(float balance) { this.balance = balance; }
    float getBalance() { return this.balance; }

    void setAccountNumber(int accountNumber) { this.accountNumber = accountNumber; }
    int getAccountNumber() { return this.accountNumber; }

    void setAccountName(String accountName) { this.accountName = accountName; }
    String getAccountName() { return this.accountName; }

    void makeDeposit(float deposit) { setBalance(getBalance() + deposit); }
    void withdrawFunds(float amount) {
        if (getBalance() - amount > 0) {
            setBalance(getBalance() - amount);
        } else {
            System.out.println("Withdrawal exceeds Balance");
        }
    }

    bankAccount() {


    }

}
