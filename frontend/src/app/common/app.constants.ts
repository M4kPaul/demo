export class AppConstants {
  private static API_BASE_URL = 'http://localhost:8080/';
  private static REDIRECT_URL = '?redirect_uri=http://localhost:8081/login';

  public static API_URL = AppConstants.API_BASE_URL + 'api/';
  public static AUTH_API = AppConstants.API_URL + 'auth/';
}
