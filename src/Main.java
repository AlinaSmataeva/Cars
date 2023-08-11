import entity.Car;
import entity.Order;
import entity.Role;
import entity.Users;
import exception.DaoException;
import model.connection.DatasourceFactory;
import model.dao.impl.CarRepositoryImpl;
import model.dao.impl.OrderRepositoryImpl;
import model.dao.impl.UsersRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private UsersRepositoryImpl usersRepository = new UsersRepositoryImpl();;
    private CarRepositoryImpl carRepository = new CarRepositoryImpl();;
    private OrderRepositoryImpl orderRepository = new OrderRepositoryImpl();
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    public void run() {
        while (true) {
            System.out.println("Меню:");
            System.out.println("1) Войти");
            System.out.println("2) Зарегистрироваться");
            System.out.println("3) Выйти");

            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    loginUser();
                    break;
                case 2:
                    registerUser();
                    break;
                case 3:
                    System.out.println("Выход из программы");
                    return;
                default:
                    System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }

    private static int getUserChoice() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Выберите действие: ");
        return scanner.nextInt();
    }

    private void loginUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        try {
            Optional<Users> user = usersRepository.signIn(username, password);

            if (user.isPresent()) {
                Role role = user.get().getRole();
                if (role != null && role.getDescription().equals("Пользователь")) {
                    List<Car> cars = carRepository.getAllCars();
                    for (Car car : cars) {
                        System.out.println(car.getName() + " - " + car.getType() + " - " + car.getCountExamples());
                    }
                    System.out.print("Выберите номер машины для заказа: ");
                    int choice = scanner.nextInt();
                    if (choice >= 1 && choice <= cars.size()) {
                        Car selectedCar = cars.get(choice - 1);
                        Order order = new Order();
                        order.setUserId(user.get().getId());
                        order.setCarId(selectedCar.getId());

                        if (orderRepository.createOrder(order)) {
                            System.out.println("Заказ оформлен.");
                        } else {
                            System.out.println("Ошибка при оформлении заказа.");
                        }

                        System.exit(0); // Завершение программы после оформления заказа
                    } else {
                        System.out.println("Неверный номер машины.");
                    }
                } else if (role != null && role.getDescription().equals("Администратор")) {
                    List<Users> users =  usersRepository.getAllUsers();
                    for (Users u : users) {
                        System.out.println(u.getUsername());
                    }
                }
            } else {
                System.out.println("Неверные имя пользователя или пароль.");
            }
        } catch (DaoException e) {
            System.out.println("Ошибка при входе: " + e.getMessage());
        }
    }

    private void registerUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите новое имя пользователя: ");
        String username = scanner.nextLine();
        System.out.print("Введите новый пароль: ");
        String password = scanner.nextLine();

        try {
           boolean success = usersRepository.signUp(username, password);
            if (success) {
                System.out.println("Пользователь успешно зарегистрирован.");
            } else {
                System.out.println("Ошибка при регистрации пользователя.");
            }
        } catch (DaoException e) {
            System.out.println("Ошибка при регистрации пользователя: " + e.getMessage());
        }
    }
}
