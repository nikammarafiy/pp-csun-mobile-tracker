using System;
using System.Collections.ObjectModel;
using System.Globalization;
using System.Linq;
using System.Windows.Threading;

using Microsoft.Phone.Controls.Maps;
using Microsoft.Phone.Controls.Maps.Platform;
using AccessingWP7Devices.Assets.Bing.Geocode;

namespace AccessingWP7Devices.Assets.Helpers
{
    /// <summary>
    /// Helper class for simplifying the process of working with the Bing geocode service.
    /// </summary>
    public static class GeocodeHelper
    {
        public static void ReverseGeocodeAddress(Dispatcher uiDispatcher, CredentialsProvider credentialsProvider, Location location, Action<GeocodeResult> completed = null, Action<GeocodeError> error = null)
        {
            completed = completed ?? (r => { });
            error = error ?? (e => { });

            // Get credentials and only then place an async call on the geocode service.
            credentialsProvider.GetCredentials(credentials =>
            {
                var request = new ReverseGeocodeRequest()
                {
                    // Pass in credentials for web services call.
                    Credentials = credentials,

                    Culture = CultureInfo.CurrentUICulture.Name,
                    Location = location,

                    // Don't raise exceptions.
                    ExecutionOptions = new ExecutionOptions
                    {
                        SuppressFaults = true
                    },
                };

                EventHandler<ReverseGeocodeCompletedEventArgs> reverseGeocodeCompleted = (s, e) =>
                {
                    try
                    {
                        if (e.Result.ResponseSummary.StatusCode != Bing.Geocode.ResponseStatusCode.Success ||
                            e.Result.Results.Count == 0)
                        {
                            // Report geocode error.
                            uiDispatcher.BeginInvoke(() => error(new GeocodeError(e)));
                        }
                        else
                        {
                            // Only report on first result.
                            var firstResult = e.Result.Results.First();
                            uiDispatcher.BeginInvoke(() => completed(firstResult));
                        }
                    }
                    catch (Exception ex)
                    {
                        uiDispatcher.BeginInvoke(() => error(new GeocodeError(ex.Message, ex)));
                    }
                };

                var geocodeClient = new GeocodeServiceClient();
                geocodeClient.ReverseGeocodeCompleted += reverseGeocodeCompleted;
                geocodeClient.ReverseGeocodeAsync(request);
            });
        }
    }
}
