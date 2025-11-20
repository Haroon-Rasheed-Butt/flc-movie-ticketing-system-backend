package com.flc.movietickets.backend.service;

import com.flc.movietickets.backend.entity.Booking;
import com.flc.movietickets.backend.entity.Show;
import com.flc.movietickets.backend.entity.User;
import com.flc.movietickets.backend.repository.BookingRepository;
import com.flc.movietickets.backend.repository.ShowRepository;
import com.flc.movietickets.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final ShowRepository showRepository;
    private final UserRepository userRepository;
    private final ShowService showService;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserIdOrderByBookingTimeDesc(userId);
    }

    public List<Booking> getBookingsByShow(Long showId) {
        return bookingRepository.findByShowId(showId);
    }

    @Transactional
    public Booking createBooking(Booking booking) {
        // Validate user exists
        User user = userRepository.findById(booking.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + booking.getUser().getId()));
        
        // Validate show exists
        Show show = showRepository.findById(booking.getShow().getId())
                .orElseThrow(() -> new RuntimeException("Show not found with id: " + booking.getShow().getId()));
        
        // Check seat availability
        if (show.getAvailableSeats() < booking.getNumberOfSeats()) {
            throw new RuntimeException("Not enough seats available. Available: " + show.getAvailableSeats());
        }
        
        // Update available seats
        boolean seatsUpdated = showService.updateAvailableSeats(show.getId(), booking.getNumberOfSeats());
        if (!seatsUpdated) {
            throw new RuntimeException("Failed to update seat availability");
        }
        
        // Calculate total amount
        Double totalAmount = show.getTicketPrice() * booking.getNumberOfSeats();
        
        booking.setUser(user);
        booking.setShow(show);
        booking.setTotalAmount(totalAmount);
        booking.setBookingTime(LocalDateTime.now());
        booking.setStatus(Booking.BookingStatus.CONFIRMED);
        
        return bookingRepository.save(booking);
    }

    @Transactional
    public void cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
        
        if (booking.getStatus() == Booking.BookingStatus.CANCELLED) {
            throw new RuntimeException("Booking is already cancelled");
        }
        
        // Return seats to show
        Show show = booking.getShow();
        show.setAvailableSeats(show.getAvailableSeats() + booking.getNumberOfSeats());
        showRepository.save(show);
        
        // Update booking status
        booking.setStatus(Booking.BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }
}
