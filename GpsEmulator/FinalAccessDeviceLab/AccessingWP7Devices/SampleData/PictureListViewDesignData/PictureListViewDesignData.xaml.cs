﻿//      *********    DO NOT MODIFY THIS FILE     *********
//      This file is regenerated by a design tool. Making
//      changes to this file can cause errors.
namespace Expression.Blend.SampleData.PictureListViewDesignData
{
	using System; 

// To significantly reduce the sample data footprint in your production application, you can set
// the DISABLE_SAMPLE_DATA conditional compilation constant and disable sample data at runtime.
#if DISABLE_SAMPLE_DATA
	internal class PictureListViewDesignData { }
#else

	public class PictureListViewDesignData : System.ComponentModel.INotifyPropertyChanged
	{
		public event System.ComponentModel.PropertyChangedEventHandler PropertyChanged;

		protected virtual void OnPropertyChanged(string propertyName)
		{
			if (this.PropertyChanged != null)
			{
				this.PropertyChanged(this, new System.ComponentModel.PropertyChangedEventArgs(propertyName));
			}
		}

		public PictureListViewDesignData()
		{
			try
			{
				System.Uri resourceUri = new System.Uri("/AccessingWP7Devices;component/SampleData/PictureListViewDesignData/PictureListViewDesignData.xaml", System.UriKind.Relative);
				if (System.Windows.Application.GetResourceStream(resourceUri) != null)
				{
					System.Windows.Application.LoadComponent(this, resourceUri);
				}
			}
			catch (System.Exception)
			{
			}
		}

		private Pictures _Pictures = new Pictures();

		public Pictures Pictures
		{
			get
			{
				return this._Pictures;
			}
		}
	}

	public class Pictures : System.Collections.ObjectModel.ObservableCollection<PicturesItem>
	{ 
	}

	public class PicturesItem : System.ComponentModel.INotifyPropertyChanged
	{
		public event System.ComponentModel.PropertyChangedEventHandler PropertyChanged;

		protected virtual void OnPropertyChanged(string propertyName)
		{
			if (this.PropertyChanged != null)
			{
				this.PropertyChanged(this, new System.ComponentModel.PropertyChangedEventArgs(propertyName));
			}
		}

		private System.Windows.Media.ImageSource _Source = null;

		public System.Windows.Media.ImageSource Source
		{
			get
			{
				return this._Source;
			}

			set
			{
				if (this._Source != value)
				{
					this._Source = value;
					this.OnPropertyChanged("Source");
				}
			}
		}

		private string _Note = string.Empty;

		public string Note
		{
			get
			{
				return this._Note;
			}

			set
			{
				if (this._Note != value)
				{
					this._Note = value;
					this.OnPropertyChanged("Note");
				}
			}
		}

		private string _Address = string.Empty;

		public string Address
		{
			get
			{
				return this._Address;
			}

			set
			{
				if (this._Address != value)
				{
					this._Address = value;
					this.OnPropertyChanged("Address");
				}
			}
		}

		private string _DateTaken = string.Empty;

		public string DateTaken
		{
			get
			{
				return this._DateTaken;
			}

			set
			{
				if (this._DateTaken != value)
				{
					this._DateTaken = value;
					this.OnPropertyChanged("DateTaken");
				}
			}
		}
	}
#endif
}
