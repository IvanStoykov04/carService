package ConnetctDatabase;



import org.example.Client;
import org.example.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.*;

import java.sql.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;               // за when(), mock()
import static org.mockito.ArgumentMatchers.anyString; // за anyString()

class ClientRequestsTest {

    //addCar
    @Test
    void addCarTrue() throws Exception {
        //създаваме Mock connection която представлява връзка с реална база данни
        //създаваме мокнат PrepareStatement
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        //тук правим връзката с базата данни и връщаме нашия мокнат PrepareStatement
        try (MockedStatic<ConnectDatabase> mockedStatic = mockStatic(ConnectDatabase.class)) {
            mockedStatic.when(ConnectDatabase::connection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

            //създаваме мокнат клиент
            Client mockClient = mock(Client.class);
            when(mockClient.getId()).thenReturn(1);

            //създаваме реален обект он ClientRequest
            ClientRequests clientRequest = new ClientRequests();
            boolean result = clientRequest.addCar(mockClient, "Toyota", "Corolla", 2020, "CA1234AB");

            //проверяваме дали методът е върнал правилната стойност
            assertTrue(result);
        }
    }

    @Test
    void addCarFalse() throws Exception {
        // Мокваме Connection и PreparedStatement
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        // Симулираме, че при изпълнението на SQL заявката възниква SQLException
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.execute()).thenThrow(new SQLException("SQL Error"));

        // Мокваме ConnectDatabase.connection()
        try (MockedStatic<ConnectDatabase> mockedStatic = mockStatic(ConnectDatabase.class)) {
            mockedStatic.when(ConnectDatabase::connection).thenReturn(mockConnection);

            Client mockClient = mock(Client.class);
            when(mockClient.getId()).thenReturn(1);

            // Тук поддаваме невалидни данни (можем да си изберем например невалиден номер за регистрация)
            ClientRequests clientRequest = new ClientRequests();
            boolean result = clientRequest.addCar(mockClient, "Toyota", "Corolla", 2020, "INVALID_NUMBER");

            // Очакваме резултата да бъде false, защото заявката е неуспешна
            assertFalse(result);
        }
    }


    @Test
    void viewRequestHistory() throws Exception {
        // Мокиране на Connection, PreparedStatement и ResultSet
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        // Мокиране на статичния метод ConnectDatabase.connection()
        try (MockedStatic<ConnectDatabase> mockedStatic = mockStatic(ConnectDatabase.class)) {
            mockedStatic.when(ConnectDatabase::connection).thenReturn(mockConnection);

            // Мокиране на PreparedStatement
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

            // Мокиране на резултатите от изпълнението на SQL заявката
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

            // Симулиране на резултатите в ResultSet
            when(mockResultSet.next()).thenReturn(true).thenReturn(false);  // Един резултат
            when(mockResultSet.getInt("request_id")).thenReturn(1);
            when(mockResultSet.getInt("car_id")).thenReturn(101);
            when(mockResultSet.getInt("user_id")).thenReturn(202);
            when(mockResultSet.getDate("completedOn")).thenReturn(Date.valueOf("2025-04-19"));
            when(mockResultSet.getDate("createdOn")).thenReturn(Date.valueOf("2025-04-18"));
            when(mockResultSet.getBoolean("modified_request")).thenReturn(true);
            when(mockResultSet.getString("status")).thenReturn("Pending");

            // Симулирай клиента
            Client mockClient = mock(Client.class);
            when(mockClient.getId()).thenReturn(202);

            // Извикай метода viewRequestHistory()
            ClientRequests clientRequests = new ClientRequests();
            boolean result = clientRequests.viewRequestHistory(mockClient);

            // Проверка дали методът е върнал правилния резултат
            assertTrue(result);  // Трябва да върне true, тъй като има поне един запис
        }
    }







}