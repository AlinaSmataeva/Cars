package entity;

import java.util.Objects;

public class Order {
    private Long id;
    private Long userId;
    private Long carId;


    public Order() {
    }

    public Order(Long id, Long userId, Long carId) {
        this.id = id;
        this.userId = userId;
        this.carId = carId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return getId() == order.getId() && Objects.equals(getUserId(), order.getUserId()) && Objects.equals(getCarId(), order.getCarId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserId(), getCarId());
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", carId=" + carId +
                '}';
    }
}
