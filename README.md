# FTCalc - Car Loan Calculator

FTCalc is a Java-based application that allows users to calculate their monthly car loan payments based on various factors such as the amount owed, trade-in value, new car price, interest rate, and loan term. The application also includes features for importing loan data from the clipboard and generating realistic loan numbers.

## Features

- **Loan Calculation**: Automatically calculates monthly car loan payments based on the user's input.
- **Equity Status**: Displays the equity status, indicating whether the user has positive or negative equity in their car loan.
- **Clipboard Import**: Allows users to import loan data from their clipboard to quickly populate the input fields.
- **Generate Realistic Data**: Generates realistic loan values based on the current input fields and automatically populates the form.
- **User Interface**: Simple and intuitive graphical user interface (GUI).

## Requirements

- Java 8 or later

## Installation

1. **Download the JAR File**: Download the latest version of the FTCalc JAR file from the [releases page](https://github.com/yourusername/FTCalc/releases).
   
2. **Run the Application**:
   - **Command Line**: Open your terminal and run the following command:
     ```bash
     java -jar FTCalc-beta-1.0.0.jar
     ```
   - **Double-click**: On supported operating systems, you can also double-click the JAR file to run the application.

## Usage

1. Enter the following details into the respective fields:
   - Amount Owed on Loan
   - Trade-In Value
   - New Car Price
   - Interest Rate (Annual %)
   - Loan Term (Months)
   
2. Click the **Calculate** button to see the estimated monthly payment and equity status.

3. Use the **Import** button to quickly import loan data from your clipboard in the following format:
```yml
owed: 0.00 
tradein: 0.00
newcar: 0.00
interest: 0.00
term: 0
```

4. Click **Generate** to get realistic loan values based on the current data.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

