package br.edu.fatecfranca.api.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CarRequest(
   @NotBlank(message = "Brand is required")
   String brand,

   @NotBlank(message = "Model is required")
   String model,

   @NotBlank(message = "Color is required")
   String color,

   @NotNull(message = "Year of manufacture is required")
   @Positive(message = "Year of manufacture must be positive")
   Long yearManufacture,

   @NotNull(message = "Imported flag is required")
   Boolean imported,

   @NotBlank(message = "Plates are required")
   @Pattern(
      regexp = "^([A-Z]{3}-?[0-9]{4}|[A-Z]{3}[0-9][A-Z0-9][0-9]{2})$",
      message = "Plate must be in the format ABC-1234 or ABC1D23"
   )
   String plates,

   @PastOrPresent(message = "Selling date cannot be in the future")
   LocalDate sellingDate,

   @Positive(message = "Selling price must be greater than zero")
   @Digits(integer = 10, fraction = 2, message = "Selling price must have up to 10 integer digits and 2 decimal places")
   BigDecimal sellingPrice,

   @Positive(message = "Customer id must be greater than zero")
   Long customerId
) {}
