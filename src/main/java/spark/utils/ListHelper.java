package spark.utils;

import java.io.IOException;
import java.util.List;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

@SuppressWarnings("rawtypes")
public enum ListHelper implements Helper<List>{
	size{
		@Override
		public CharSequence apply(List arg0, Options arg1) throws IOException {
			if (arg0==null) return "0";
			return Integer.toString(arg0.size());
		}
	}

}
