package id.tasking.data.local

import id.tasking.domain.model.Task

fun TaskEntity.toDomain(): Task =
   Task(id, title, description, isCompleted)

fun Task.toEntity(): TaskEntity =
    TaskEntity(id, title, description, isCompleted)
