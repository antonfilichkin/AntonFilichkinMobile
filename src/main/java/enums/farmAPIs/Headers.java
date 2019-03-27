package enums.farmAPIs;

public enum Headers {
        AUTHORIZATION("Authorization"),
        BEARER("Bearer");

        public final String header;

        Headers(String header) {
            this.header = header;
        }
}
