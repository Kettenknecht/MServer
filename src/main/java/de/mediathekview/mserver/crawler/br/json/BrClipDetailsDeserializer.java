/*
 * BrClipDetailsDeserializer.java
 * 
 * Projekt    : MServer
 * erstellt am: 19.12.2017
 * Autor      : Sascha
 * 
 * (c) 2017 by Sascha Wiegandt
 */
package de.mediathekview.mserver.crawler.br.json;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import de.mediathekview.mlib.daten.Film;
import de.mediathekview.mserver.crawler.basic.AbstractCrawler;
import de.mediathekview.mserver.crawler.br.data.BrGraphQLElementNames;
import de.mediathekview.mserver.crawler.br.data.BrGraphQLNodeNames;
import de.mediathekview.mserver.crawler.br.data.BrID;

public class BrClipDetailsDeserializer implements JsonDeserializer<Optional<Film>> {

  private AbstractCrawler crawler;
  private BrID id;
  
  public BrClipDetailsDeserializer(AbstractCrawler crawler, BrID id) {
    super();
    this.crawler = crawler;
    this.id = id;
  }
  
  /*
   * Pseudonymized Example to see the Nodes filled
   * 
   * {
   *   "data": {
   *     "viewer": {
   *       "clipDetails": {
   *         "__typename": "Programme",
   *         "id": "av:5a0603ce8c16b90012f4bc49",
   *         "title": "Der Titel ist der \"Hammer\"",
   *         "kicker": "Hammertime vom 25. Oktober",
   *         "duration": 844,
   *         "ageRestriction": 0,
   *         "description": "Dies ist ein Typoblindtext. An ihm kann man sehen, ob alle Buchstaben da sind und wie sie aussehen. Manchmal benutzt man Worte wie Hamburgefonts, Rafgenduks oder Handgloves, um Schriften zu testen.\n\nManchmal Sätze, die alle Buchstaben des Alphabets enthalten - man nennt diese Sätze »Pangrams«. Sehr bekannt ist dieser: The quick brown fox jumps over the lazy old dog.\n\nOft werden in Typoblindtexte auch fremdsprachige Satzteile eingebaut (AVAIL® and Wefox™ are testing aussi la Kerning), um dieWirkung in anderen Sprachen zu testen. In Lateinisch sieht zum Beispiel fast jede Schrift gut aus.\n\nQuod erat demonstrandum. Seit 1975 fehlen in den meisten Testtexten die Zahlen, weswegen nach TypoGb. 204 § ab dem Jahr 2034 Zahlen in 86 der Texte zur Pflicht werden.\n\nNichteinhaltung wird mit bis zu 245 € oder 368 $ bestraft. Genauso wichtig in sind mittlerweile auch Âçcèñtë, die in neueren Schriften aber fast immer enthalten sind. Ein wichtiges aber schwierig zu integrierendes Feld sindOpenType-Funktionalitäten.\n\nJe nach Software und Voreinstellungen können eingebaute Kapitälchen, Kerning oder Ligaturen (sehr pfiffig) nicht richtig dargestellt werden. Dies ist ein Typoblindtext. An ihm kann man sehen, ob alle Buchstaben da sind und wie sie aussehen.",
   *         "shortDescription": "Überall dieselbe alte Leier. Das Layout ist fertig, der Text lässt auf sich warten. Damit das Layout nun nicht nackt im Raume steht und sich klein und leer vorkommt, springe ich ein: der Blindtext. Genau zu diesem Zwecke erschaffen, immer im Schatten meines großen Bruders »Lorem Ipsum«, freue ich mich jedes Mal, wenn Sie ein paar Zeilen lesen.",
   *         "slug": "hammertime-vom-25-oktober-ein-wiedersehen-mit-tick-trick-und-track",
   *         "authors": {
   *           "count": 1,
   *           "edges": [
   *             {
   *               "node": {
   *                 "id": "av:59f0b9ebe9d83c0018fd63cb",
   *                 "name": "Dagobert Duck, Donald Duck, Daisy Duck"
   *               }
   *             }
   *           ]
   *         },
   *         "subjects": {
   *           "count": 0,
   *           "edges": []
   *         },
   *         "tags": {
   *           "count": 0,
   *           "edges": []
   *         },
   *         "executiveProducers": {
   *           "count": 0,
   *           "edges": []
   *         },
   *         "credits": {
   *           "count": 1,
   *           "edges": [
   *             {
   *               "node": {
   *                 "id": "av:59f0b9ebe9d83c0018fd63cb",
   *                 "name": "Dagobert Duck, Donald Duck, Daisy Duck"
   *               }
   *             }
   *           ]
   *         },
   *         "categorizations": {
   *           "count": 2,
   *           "edges": [
   *             {
   *               "node": {
   *                 "id": "av:http://ard.de/ontologies/categories#kultur"
   *               }
   *             },
   *             {
   *               "node": {
   *                 "id": "av:http://ard.de/ontologies/categories#kino"
   *               }
   *             }
   *           ]
   *         },
   *         "genres": {
   *           "count": 0,
   *           "edges": []
   *         },
   *         "videoFiles": {
   *           "count": 7,
   *           "edges": [
   *             {
   *               "node": {
   *                 "id": "av:59f0b9ebe9d83c0018fd63c5",
   *                 "publicLocation": "https://cdn-storage.br.de/MUJIuUOVBwQIbtCCBLzGiLC1uwQoNA4p_A0S/_AES/_A4G5A8H9U1S/fec59c2f-61ef-40de-99a0-305414ed10c6_X.mp4",
   *                 "accessibleIn": {
   *                   "count": 0,
   *                   "edges": []
   *                 },
   *                 "videoProfile": {
   *                   "id": "av:http://ard.de/ontologies/audioVideo#VideoProfile_HD",
   *                   "height": 720,
   *                   "width": 1280
   *                 }
   *               }
   *             },
   *             {
   *               "node": {
   *                 "id": "av:59f0b9ebe9d83c0018fd63c3",
   *                 "publicLocation": "https://cdn-storage.br.de/MUJIuUOVBwQIbtCCBLzGiLC1uwQoNA4p_A0S/_AES/_A4G5A8H9U1S/fec59c2f-61ef-40de-99a0-305414ed10c6_C.mp4",
   *                 "accessibleIn": {
   *                   "count": 0,
   *                   "edges": []
   *                 },
   *                 "videoProfile": {
   *                   "id": "av:http://ard.de/ontologies/audioVideo#VideoProfile_Premium",
   *                   "height": 540,
   *                   "width": 969
   *                 }
   *               }
   *             },
   *             {
   *               "node": {
   *                 "id": "av:59f0b9ebe9d83c0018fd63c4",
   *                 "publicLocation": "https://cdn-storage.br.de/MUJIuUOVBwQIbtCCBLzGiLC1uwQoNA4p_A0S/_AES/_A4G5A8H9U1S/fec59c2f-61ef-40de-99a0-305414ed10c6_E.mp4",
   *                 "accessibleIn": {
   *                   "count": 0,
   *                   "edges": []
   *                 },
   *                 "videoProfile": {
   *                   "id": "av:http://ard.de/ontologies/audioVideo#VideoProfile_Large",
   *                   "height": 360,
   *                   "width": 640
   *                 }
   *               }
   *             },
   *             {
   *               "node": {
   *                 "id": "av:59f0b9ebe9d83c0018fd63c2",
   *                 "publicLocation": "https://cdn-storage.br.de/MUJIuUOVBwQIbtCCBLzGiLC1uwQoNA4p_A0S/_AES/_A4G5A8H9U1S/fec59c2f-61ef-40de-99a0-305414ed10c6_B.mp4",
   *                 "accessibleIn": {
   *                   "count": 0,
   *                   "edges": []
   *                 },
   *                 "videoProfile": {
   *                   "id": "av:http://ard.de/ontologies/audioVideo#VideoProfile_Standard",
   *                   "height": 288,
   *                   "width": 512
   *                 }
   *               }
   *             },
   *             {
   *               "node": {
   *                 "id": "av:59f0b9ebe9d83c0018fd63c1",
   *                 "publicLocation": "https://cdn-storage.br.de/MUJIuUOVBwQIbtCCBLzGiLC1uwQoNA4p_A0S/_AES/_A4G5A8H9U1S/fec59c2f-61ef-40de-99a0-305414ed10c6_A.mp4",
   *                 "accessibleIn": {
   *                   "count": 0,
   *                   "edges": []
   *                 },
   *                 "videoProfile": {
   *                   "id": "av:http://ard.de/ontologies/audioVideo#VideoProfile_Mobile",
   *                   "height": 270,
   *                   "width": 480
   *                 }
   *               }
   *             },
   *             {
   *               "node": {
   *                 "id": "av:59f0b9ebe9d83c0018fd63c0",
   *                 "publicLocation": "https://cdn-storage.br.de/MUJIuUOVBwQIbtCCBLzGiLC1uwQoNA4p_A0S/_AES/_A4G5A8H9U1S/fec59c2f-61ef-40de-99a0-305414ed10c6_0.mp4",
   *                 "accessibleIn": {
   *                   "count": 0,
   *                   "edges": []
   *                 },
   *                 "videoProfile": {
   *                   "id": "av:http://ard.de/ontologies/audioVideo#VideoProfile_Mobile_S",
   *                   "height": 180,
   *                   "width": 320
   *                 }
   *               }
   *             },
   *             {
   *               "node": {
   *                 "id": "av:59f0b9ebe9d83c0018fd63c6",
   *                 "publicLocation": "https://br-i.akamaihd.net/i/MUJIuUOVBwQIbtCCBLzGiLC1uwQoNA4p_A0S/_AES/_A4G5A8H9U1S/fec59c2f-61ef-40de-99a0-305414ed10c6_,0,A,B,E,C,X,.mp4.csmil/master.m3u8?__b__\u003d200",
   *                 "accessibleIn": {
   *                   "count": 0,
   *                   "edges": []
   *                 },
   *                 "videoProfile": {
   *                   "id": "av:http://ard.de/ontologies/audioVideo#VideoProfile_HLS"
   *                 }
   *               }
   *             }
   *           ]
   *         },
   *         "captionFiles": {
   *           "count": 0,
   *           "edges": []
   *         },
   *         "episodeOf": {
   *           "id": "av:584f4bfd3b467900117be493",
   *           "title": "hammertime",
   *           "kicker": "hammertime",
   *           "scheduleInfo": "Mittwochs um 00.15 Uhr im BR Fernsehen. Freitags 00.15 Uhr in ARD-alpha, Dienstags um 21.45 Uhr in 3sat",
   *           "shortDescription": "Hammertime - das Bauseminar für IT-Profis. Der richtige Umgang mit Werkzeugen jede Woche neues übers Heimwerkern."
   *         },
   *         "broadcasts": {
   *           "edges": [
   *             {
   *               "node": {
   *                 "__typename": "BroadcastEvent",
   *                 "start": "2017-10-25T22:15:00.000Z",
   *                 "id": "av:5a0603ce8c16b90012f4bc49|5a0603ce8c16b90012f4bc43"
   *               }
   *             }
   *           ]
   *         }
   *       },
   *       "id": "Viewer:__VIEWER"
   *     }
   *   }
   * }
   * 
   */

  
  
  @Override
  public Optional<Film> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    
    JsonObject rootObject = json.getAsJsonObject();
    
    Optional<JsonObject> clipDetails = getClipDetailsNode(rootObject);
    if(clipDetails.isPresent()) {
      JsonObject clipDetailRoot = clipDetails.get();
      
      Optional<String>          titel           = getTitel(clipDetailRoot);
      Optional<String>          thema           = getThema(clipDetailRoot);
      Optional<LocalDateTime>   sendeZeitpunkt  = getSendeZeitpunkt(clipDetailRoot);
      Optional<Duration>        clipLaenge      = getClipLaenge(clipDetailRoot);
      
      if(titel.isPresent() && thema.isPresent() && sendeZeitpunkt.isPresent() && clipLaenge.isPresent()) {
        Film currentFilm = new Film(UUID.randomUUID(), this.crawler.getSender(), titel.get(), thema.get(), sendeZeitpunkt.get(), clipLaenge.get());
        
        return Optional.of(currentFilm);
      }
    }
    this.crawler.incrementAndGetErrorCount();
    return Optional.empty();
    
  }

  private Optional<Duration> getClipLaenge(JsonObject clipDetailRoot) {
    // TODO Auto-generated method stub
    return null;
  }

  private Optional<LocalDateTime> getSendeZeitpunkt(JsonObject clipDetailRoot) {
    // TODO Auto-generated method stub
    return null;
  }

  private Optional<String> getThema(JsonObject clipDetailRoot) {
    // TODO Auto-generated method stub
    return null;
  }

  private Optional<JsonObject> getClipDetailsNode(JsonObject rootObject) {
    if(!rootObject.has(BrGraphQLNodeNames.RESULT_ROOT_NODE.getName())) {
      return Optional.empty();
    }
    JsonObject dataNode = rootObject.getAsJsonObject(BrGraphQLNodeNames.RESULT_ROOT_NODE.getName());

    if(!dataNode.has(BrGraphQLNodeNames.RESULT_ROOT_BR_NODE.getName())) {
      return Optional.empty();
    }
    JsonObject viewerNode = dataNode.getAsJsonObject(BrGraphQLNodeNames.RESULT_ROOT_BR_NODE.getName());

    if(!viewerNode.has(BrGraphQLNodeNames.RESULT_CLIP_DETAILS_ROOT.getName()) || !viewerNode.isJsonObject()) {
      return Optional.empty();
    }
    return Optional.of(viewerNode.getAsJsonObject(BrGraphQLNodeNames.RESULT_CLIP_DETAILS_ROOT.getName()));
    
  }
  
  private Optional<String>  getTitel(JsonObject clipDetailRoot) {
    if(!clipDetailRoot.has(BrGraphQLElementNames.STRING_CLIP_TITLE.getName())) {
      return Optional.empty();
    }
    JsonPrimitive titleElement = clipDetailRoot.getAsJsonPrimitive(BrGraphQLElementNames.STRING_CLIP_TITLE.getName()); 
    
    return Optional.of(titleElement.getAsString());
  }
  
  
  
}