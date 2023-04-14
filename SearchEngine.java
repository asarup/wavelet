import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    ArrayList<String> contents = new ArrayList<>();

    public String handleRequest(URI url){
        
        if(url.getQuery() != null) {
            String[] parameters = url.getQuery().split("=");
            if(url.getPath().contains("/add")) {
                if(parameters[0].equals("s")) {
                    contents.add(parameters[1]);
                    return parameters[1] + " added to list!";
                }
            } else if(url.getPath().contains("/search")) {
                String matches = "";
                if(parameters[0].equals("s")) {
                    for(int i = 0; i < contents.size(); i++) {
                        if(contents.get(i).contains(parameters[1])) {
                            matches += contents.get(i) + "\n";
                        }
                    }
                    return "Found these matches:\n" + matches;
                }
            }
        }
        else if(url.getPath().equals("/")){
            return "Current contents: \n" + contents.toString();
        }

        return "Invalid path given";

    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
