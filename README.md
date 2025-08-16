# Friendly EVM

A lightweight implementation of the Ethereum Virtual Machine (EVM) written in Java with Spring Boot.  
This project is meant for educational and experimental purposes — it simulates execution of EVM bytecode, manages the stack, memory, storage, and supports a growing subset of opcodes.

---

## 📦 Project Structure

- **`models/`** – Core domain objects (`EVMContext`, `EVMStack`, `EVMCode`, etc.)
- **`service/vm/`** – Service classes that handle specific opcode families (`ArithmeticService`, `JumpService`, `StorageService`, etc.)
- **`vm/`** – The `EVM` engine that coordinates execution.
- **`runner/`** – Spring Boot `CommandLineRunner` entry point.
- **`config/`** – Spring configuration classes that wire up `EVMContext` and related beans.

---

## 🚀 Running the App

### 1. From the Command Line

Make sure you have **Java 17+** and **Maven** installed.
# Clone the repo
```bash
git clone https://github.com/yourusername/friendly-evm.git
cd friendly-evm
```
# Build the project
```bash
mvn clean package
```
# Run with the "local" profile
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local

```

## Successful Run!
[success_run.JPG](success_run.JPG)