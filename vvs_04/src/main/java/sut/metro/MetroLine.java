package sut.metro;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Software Verification and Validation
 * 
 * Master of Science in Computer Engineering,
 * University of Lisbon,
 * Faculty of Sciences,
 * Department of Informatics
 * 
 * @author Eduardo Marques
 * @version $Id: MetroLine.java 313 2016-03-21 12:46:58Z vv $
 */
public enum MetroLine {
	
  AMARELA("Odivelas", "Senhor Roubado", "Ameixoeira", "Lumiar",
      "Quinta das Conchas", "Campo Grande", "Cidade Universitária",
      "Entre Campos", "Campo Pequeno", "Saldanha", "Picoas",
      "Marquês de Pombal", "Rato"),

  AZUL("Amadora Este", "Alfornelos", "Pontinha", "Carnide",
      "Colégio Militar/Luz", "Alto dos Moinhos", "Laranjeiras",
      "Jardim Zoológico", "Praça de Espanha", "São Sebastião", "Parque",
      "Marquês de Pombal", "Avenida", "Restauradores", "Baixa-Chiado",
      "Terreiro do Paço", "Santa Apolónia"), 
      
  VERMELHA("São Sebastião",
      "Saldanha", "Alameda", "Olaias", "Bela Vista", "Chelas", "Olivais",
      "Cabo Ruivo", "Oriente", "Moscavide", "Encarnação", "Aeroporto"), 
      
  VERDE(
      "Telheiras", "Campo Grande", "Alvalade", "Roma", "Areeiro",  "Alameda",  
      "Arroios", "Anjos", "Intendente", "Martim Moniz", "Rossio",
      "Baixa-Chiado", "Cais do Sodré")
  ;

  /**
   * List of stations.
   */
  private final List<String> stations;

  /**
   * Constructor.
   * 
   * @param stations
   *          Stations in this line.
   */
  private MetroLine(String... stations) {
    this.stations = Collections.unmodifiableList(Arrays.asList(stations));

  }

  /**
   * Get a list of all stations for this line.
   * 
   * @return An immutable list of all station names.
   */
  public List<String> getStations() {
    return stations;
  }
  
  /**
   * Get first station in this line.
   * @return A station name.
   */
  public String getFirstStation() {
    return stations.get(0);
  }
  
  /**
   * Get last station in this line.
   * @return A station name.
   */
  public String getLastStation() {
    return stations.get(stations.size()-1);
  }
  
  /**
   * Get station count.
   * @return The number of stations in this line.
   */
  public int getStationCount() {
    return stations.size();
  }

  /**
   * Check if station belongs to this line.
   * 
   * @param station
   *          Station name.
   * @return True if station belongs to this line.
   */
  public boolean hasStation(String station) {
    return stations.stream().anyMatch(s -> s.equals(station));
  }
}