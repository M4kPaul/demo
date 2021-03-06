export class AppConstants {
  private static API_BASE_URL = 'http://127.0.0.1:8080/'; // update
  private static REDIRECT_URL = '?redirect_uri=http://127.0.0.1:8081/login';

  public static API_URL = AppConstants.API_BASE_URL + 'api/';
  public static AUTH_API = AppConstants.API_URL + 'auth/';
}
