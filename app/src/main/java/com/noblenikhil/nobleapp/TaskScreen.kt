package com.noblenikhil.nobleapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class SortOption {
    DUE_DATE, PRIORITY, TITLE
}

enum class StatusFilter {
    ALL, PENDING, COMPLETED
}

@Composable
fun TaskScreen(
    username: String = "Student",
    onLogout: () -> Unit = {}
) {
    // Initial sample tasks
    val tasks = remember {
        mutableStateListOf(
            Task(
                title = "Submit Math Assignment",
                notes = "Exercises 1 to 10 from Chapter 4",
                subject = "Mathematics",
                dueDate = "2025-04-05",
                priority = Priority.HIGH,
                isCompleted = false
            ),
            Task(
                title = "CS101 Lab Report",
                notes = "Submit PDF on university portal",
                subject = "Computer Science",
                dueDate = "2025-04-08",
                priority = Priority.MEDIUM,
                isCompleted = true
            ),
            Task(
                title = "Physics Quiz Prep",
                notes = "Revise thermodynamics formulas",
                subject = "Physics",
                dueDate = "2025-04-12",
                priority = Priority.LOW,
                isCompleted = false
            )
        )
    }

    var showAddDialog by remember { mutableStateOf(false) }
    var taskToEdit by remember { mutableStateOf<Task?>(null) }

    var statusFilter by remember { mutableStateOf(StatusFilter.ALL) }
    var selectedSubjectFilter by remember { mutableStateOf("All") }
    var sortOption by remember { mutableStateOf(SortOption.DUE_DATE) }

    // Dynamic list of subjects for filtering
    val subjects = remember(tasks) {
        listOf("All") + tasks.map { it.subject }.filter { it.isNotBlank() }.distinct()
    }

    // Filtered and sorted tasks
    val filteredTasks = tasks.filter { task ->
        val matchesStatus = when (statusFilter) {
            StatusFilter.ALL -> true
            StatusFilter.PENDING -> !task.isCompleted
            StatusFilter.COMPLETED -> task.isCompleted
        }
        val matchesSubject = selectedSubjectFilter == "All" || task.subject.equals(selectedSubjectFilter, ignoreCase = true)
        matchesStatus && matchesSubject
    }.sortedWith { t1, t2 ->
        when (sortOption) {
            SortOption.DUE_DATE -> t1.dueDate.compareTo(t2.dueDate)
            SortOption.PRIORITY -> t1.priority.ordinal.compareTo(t2.priority.ordinal)
            SortOption.TITLE -> t1.title.compareTo(t2.title, ignoreCase = true)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "🎓 STUDENT TASK MANAGER",
                    color = Color.LightGray,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Welcome, $username!",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic
                )
            }
            Button(
                onClick = onLogout,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray,
                    contentColor = Color.LightGray
                )
            ) {
                Text("Logout", fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add Task Button & Quick Filters
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { showAddDialog = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.DarkGray
                )
            ) {
                Text("+ Add New Task", fontWeight = FontWeight.Bold)
            }

            Text(
                text = "${tasks.count { !it.isCompleted }} pending",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Filter and Sort Control Bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1E1E1E), shape = RoundedCornerShape(8.dp))
                .padding(10.dp)
        ) {
            // Status Filter Tabs
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatusFilter.values().forEach { filter ->
                    val isSelected = statusFilter == filter
                    Text(
                        text = filter.name,
                        color = if (isSelected) Color.White else Color.Gray,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 13.sp,
                        modifier = Modifier
                            .clickable { statusFilter = filter }
                            .padding(4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Sort & Subject dropdown row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Sort selector
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Sort: ", color = Color.Gray, fontSize = 12.sp)
                    SortDropdown(
                        selectedOption = sortOption,
                        onOptionSelected = { sortOption = it }
                    )
                }

                // Subject filter dropdown
                if (subjects.size > 1) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Subject: ", color = Color.Gray, fontSize = 12.sp)
                        SubjectDropdown(
                            subjects = subjects,
                            selectedSubject = selectedSubjectFilter,
                            onSubjectSelected = { selectedSubjectFilter = it }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Task List
        if (filteredTasks.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No tasks found.\nClick '+ Add New Task' to get started!",
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredTasks, key = { it.id }) { task ->
                    TaskItem(
                        task = task,
                        onToggleComplete = {
                            val index = tasks.indexOfFirst { t -> t.id == task.id }
                            if (index != -1) {
                                tasks[index] = tasks[index].copy(isCompleted = !task.isCompleted)
                            }
                        },
                        onEditClick = { taskToEdit = task },
                        onDeleteClick = { tasks.removeIf { t -> t.id == task.id } }
                    )
                }
            }
        }
    }

    // Add Task Dialog
    if (showAddDialog) {
        TaskDialog(
            dialogTitle = "Add New Task",
            initialTask = null,
            onDismiss = { showAddDialog = false },
            onSave = { newTask ->
                tasks.add(newTask)
                showAddDialog = false
            }
        )
    }

    // Edit Task Dialog
    taskToEdit?.let { task ->
        TaskDialog(
            dialogTitle = "Edit Task",
            initialTask = task,
            onDismiss = { taskToEdit = null },
            onSave = { updatedTask ->
                val index = tasks.indexOfFirst { it.id == task.id }
                if (index != -1) {
                    tasks[index] = updatedTask.copy(id = task.id)
                }
                taskToEdit = null
            }
        )
    }
}

@Composable
fun TaskItem(
    task: Task,
    onToggleComplete: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF181818), shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFF333333), shape = RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = task.isCompleted,
                    onCheckedChange = { onToggleComplete() },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.LightGray,
                        checkmarkColor = Color.Black,
                        uncheckedColor = Color.Gray
                    )
                )

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = task.title,
                        color = if (task.isCompleted) Color.Gray else Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                    )

                    if (task.subject.isNotBlank()) {
                        Text(
                            text = "Course: ${task.subject}",
                            color = Color.LightGray,
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }

                // Priority Badge
                PriorityBadge(priority = task.priority)
            }

            if (task.notes.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = task.notes,
                    color = Color.LightGray,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(start = 48.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Due Date
                if (task.dueDate.isNotBlank()) {
                    Text(
                        text = "📅 Due: ${task.dueDate}",
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 48.dp)
                    )
                } else {
                    Spacer(modifier = Modifier.width(1.dp))
                }

                // Action Buttons
                Row {
                    Text(
                        text = "✏️ Edit",
                        color = Color.LightGray,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .clickable { onEditClick() }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                    Text(
                        text = "🗑️ Delete",
                        color = Color(0xFFFF6B6B),
                        fontSize = 12.sp,
                        modifier = Modifier
                            .clickable { onDeleteClick() }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PriorityBadge(priority: Priority) {
    val (bgColor, textColor) = when (priority) {
        Priority.HIGH -> Color(0xFF8B0000) to Color.White
        Priority.MEDIUM -> Color(0xFFB8860B) to Color.White
        Priority.LOW -> Color(0xFF006400) to Color.White
    }

    Box(
        modifier = Modifier
            .background(bgColor, shape = RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(
            text = priority.name,
            color = textColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SortDropdown(
    selectedOption: SortOption,
    onOptionSelected: (SortOption) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Text(
            text = when (selectedOption) {
                SortOption.DUE_DATE -> "Due Date 🔽"
                SortOption.PRIORITY -> "Priority 🔽"
                SortOption.TITLE -> "Title 🔽"
            },
            color = Color.White,
            fontSize = 12.sp,
            modifier = Modifier
                .clickable { expanded = true }
                .padding(4.dp)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color(0xFF2A2A2A))
        ) {
            SortOption.values().forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.name.replace("_", " "), color = Color.White, fontSize = 12.sp) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun SubjectDropdown(
    subjects: List<String>,
    selectedSubject: String,
    onSubjectSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Text(
            text = "$selectedSubject 🔽",
            color = Color.White,
            fontSize = 12.sp,
            modifier = Modifier
                .clickable { expanded = true }
                .padding(4.dp)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color(0xFF2A2A2A))
        ) {
            subjects.forEach { subject ->
                DropdownMenuItem(
                    text = { Text(subject, color = Color.White, fontSize = 12.sp) },
                    onClick = {
                        onSubjectSelected(subject)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun TaskDialog(
    dialogTitle: String,
    initialTask: Task?,
    onDismiss: () -> Unit,
    onSave: (Task) -> Unit
) {
    var title by remember { mutableStateOf(initialTask?.title ?: "") }
    var notes by remember { mutableStateOf(initialTask?.notes ?: "") }
    var subject by remember { mutableStateOf(initialTask?.subject ?: "") }
    var dueDate by remember { mutableStateOf(initialTask?.dueDate ?: "") }
    var priority by remember { mutableStateOf(initialTask?.priority ?: Priority.MEDIUM) }

    var errorMessage by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF1E1E1E),
        title = {
            Text(
                text = dialogTitle,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                if (errorMessage.isNotBlank()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                // Title Input
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Task Title *") },
                    colors = darkTextFieldColors(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Subject Input
                OutlinedTextField(
                    value = subject,
                    onValueChange = { subject = it },
                    label = { Text("Subject / Course (Optional)") },
                    colors = darkTextFieldColors(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Due Date Input
                OutlinedTextField(
                    value = dueDate,
                    onValueChange = { dueDate = it },
                    label = { Text("Due Date e.g. 2025-04-10 (Optional)") },
                    colors = darkTextFieldColors(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Notes Input
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes / Details (Optional)") },
                    colors = darkTextFieldColors(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Priority Selection
                Text("Priority Level:", color = Color.LightGray, fontSize = 13.sp)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Priority.values().forEach { p ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = priority == p,
                                onClick = { priority = p },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color.LightGray,
                                    unselectedColor = Color.Gray
                                )
                            )
                            Text(
                                text = p.name,
                                color = Color.White,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isBlank()) {
                        errorMessage = "Title cannot be empty!"
                    } else {
                        onSave(
                            Task(
                                title = title.trim(),
                                notes = notes.trim(),
                                subject = subject.trim(),
                                dueDate = dueDate.trim(),
                                priority = priority,
                                isCompleted = initialTask?.isCompleted ?: false
                            )
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.DarkGray
                )
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel", color = Color.LightGray)
            }
        }
    )
}

@Composable
private fun darkTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    focusedLabelColor = Color.LightGray,
    unfocusedLabelColor = Color.Gray,
    focusedBorderColor = Color.White,
    unfocusedBorderColor = Color.Gray
)

@Preview(showBackground = true)
@Composable
fun TaskScreenPreview() {
    Surface(color = Color.Black) {
        TaskScreen(username = "Nikhil")
    }
}
