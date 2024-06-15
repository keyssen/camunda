package ras.demo.camunda.restService.productService.model;

/**
 * Перечисление для статусов заказа.
 */
public enum OrderStatus {
    CREATED,    // Создан
    CONFIRMED,  // Подтвержден
    CANCELLED,  // Отменен
    DONE,       // Выполнен
    REJECTED,    // Отклонен
    PROCESSING // В процессе
}