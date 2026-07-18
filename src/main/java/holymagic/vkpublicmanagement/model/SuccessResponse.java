package holymagic.vkpublicmanagement.model;

public record SuccessResponse(String message) {

    public static SuccessResponse success(String message) {
        return new SuccessResponse(message);
    }

}
