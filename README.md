# ToDo List App - Tasking

A minimal Android ToDo List app built with **Jetpack Compose** and **Clean Architecture**.  
Allows user to **add, edit, delete, and complete tasks**. Tasks persist across screen rotations, local storage and device restarts.

## Video Demo 
[Video Demo](https://drive.google.com/file/d/1m_6Bz2JDPM5AkBr6_g6J6H0aFwqRene0/view?usp=sharing)

---

## APK File

[Download Tasking APK](https://drive.google.com/file/d/1vPwB81e3LVuD3BQTuSoCTADW7Ihgy1dC/view?usp=sharing)

---

## 🏗 Architecture

This project uses **Clean Architecture**:

1. **Domain Layer**  
   - Contains `Task` model and `UseCases` (Add, Edit, Delete, Complete, GetTasks).  
   - Pure Kotlin, no Android dependencies.

2. **Data Layer**  
   - Room database for local persistence.  
   - Repository interfaces and implementations.  
   - Handles data mapping between Room entities and domain models.

3. **Presentation Layer**  
   - Jetpack Compose UI: `TaskScreen`, `TaskItem`, `TaskBottomSheet`.  
   - `TaskViewModel` holds state and survives rotation.  
   - Hilt for dependency injection.

---

## 🛠 Tech Stack

- **Language:** Kotlin  
- **UI:** Jetpack Compose (Material3)  
- **Architecture:** Clean Architecture (Domain, Data, Presentation)  
- **Persistence:** Room database (Local storage)  
- **DI:** Hilt  
- **State management:** ViewModel + StateFlow 

---

## ⚡ Features

- Add a task with BottomSheet.  
- Edit task with BottomSheet.  
- Delete task.  
- Complete task (Strikethrough and not editable).  
- Tasks persist on screen rotation.  
- Responsive UI with Material3 components.  

---

## 🚀 How to Run

- Clone the repository:

```bash
git clone https://github.com/FajarSubeki/Tasking.git
open and run project in Android Studio

