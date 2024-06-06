package br.com.gabriel.desafio_celcoin.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class DateUtils {
    private static final Map<Month, List<Integer>> feriadosBrasil = Map.of(
        Month.JANUARY, Arrays.asList(1),
        Month.APRIL, Arrays.asList(21),
        Month.MAY, Arrays.asList(1),
        Month.SEPTEMBER, Arrays.asList(7),
        Month.OCTOBER, Arrays.asList(12),
        Month.NOVEMBER, Arrays.asList(2, 15, 20),
        Month.DECEMBER, Arrays.asList(25)
    );
    
    public static LocalDate calcularDataProximaParcela(LocalDate ultimaParcela, Short diaDoVencimento) {
        var temp = ultimaParcela.plusMonths(1);
        var dia = (int) diaDoVencimento;

        while (!dataValida(temp.getMonth(), dia, temp.isLeapYear(), temp.getDayOfWeek())) {
            temp = temp.plusDays(1);
            dia = temp.getDayOfMonth();
        } 

        return temp;
    }

    private static boolean dataValida(Month mes, int dia, boolean isAnobisexto, DayOfWeek diaSemana) {
        if (mes.equals(Month.FEBRUARY) && dia > 28 && (dia > 29 || !isAnobisexto)) {
            return false;
        }

        if (dia == 31 && Arrays.asList(Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER).contains(mes)) {
            return false;
        }

        if (feriadosBrasil.keySet().contains(mes) && feriadosBrasil.get(mes).contains(dia)) {
            return false;
        }

        if (diaSemana.equals(DayOfWeek.SUNDAY) || diaSemana.equals(DayOfWeek.SATURDAY)) {
            return false;
        }

        return true;
    }
}
